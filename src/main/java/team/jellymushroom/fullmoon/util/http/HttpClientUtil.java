package team.jellymushroom.fullmoon.util.http;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.Asserts;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class HttpClientUtil {

  static {
    System.setProperty("sun.net.spi.nameservice.provider.1", "dns,uc");
    System.setProperty("sun.net.spi.nameservice.provider.2", "default");
  }

  private static CloseableHttpClient httpClient = createHttpClient();

  private static CloseableHttpClient createHttpClient() {
    // 优先使用自己的dns解析服务，用于支持自定义dns
    ConnectionConfig connectionConfig = ConnectionConfig.custom()
        .setCharset(Consts.UTF_8)
        .build();
    SocketConfig socketConfig = SocketConfig.custom()
        .setTcpNoDelay(true)
        .setSoKeepAlive(true)
        .setSoReuseAddress(true)
        .setSoTimeout(3000)
        .build();

    return HttpClientBuilder.create()
        .setDefaultConnectionConfig(connectionConfig)
        .setDefaultSocketConfig(socketConfig)
        .setMaxConnTotal(2000)
        .setMaxConnPerRoute(200)
        .setRetryHandler(new DefaultHttpRequestRetryHandler(3,true))
        .build();
  }

  private String url;
  private ImmutableMap.Builder<String, String> urlParams = ImmutableMap.builder();
  private ImmutableMap.Builder<String, String> formParams = ImmutableMap.builder();
  private ImmutableMultimap.Builder<String, String> multiParams = ImmutableMultimap.builder();
  private ImmutableSet.Builder<String> urlParamExclusion = ImmutableSet.builder();
  private String body = null;
  private Object objectBody = null;
  private byte[] byteBody = null;
  private RequestMethod method = RequestMethod.GET;
  private ImmutableMap.Builder<String, String> headers = ImmutableMap.builder();
  private ContentType contentType = ContentType.APPLICATION_JSON;
  private int connTime = 3000;
  private int readTime = 3000;
  private boolean logMark = false;
  private HttpServletRequest requestToRelay;
  private Signer signer = null;
  private Set<String> headsWontRelay = Sets.newHashSet("accept", "accept-encoding", "accept-language", "connection", "content-length", "content-type", "host");

  public HttpResult exec() {
    Asserts.notBlank(url, "url");
    String urlWithParam = appendParamsToUrl();

    switch (method) {
      case GET:
        return getInner(urlWithParam);
      case PUT:
        return putInner(urlWithParam);
      case DELETE:
        return deleteInner(urlWithParam);
      case POST:
        return postInner(urlWithParam);
      case PATCH:
        return patchInner(urlWithParam);
      default:
        return new HttpResult(new IllegalArgumentException("method not supported!" + method), this);
    }
  }

  private HttpResult postInner(String urlWithParam) {
    HttpPost post = new HttpPost(urlWithParam);
    return doRequest(post);
  }

  private HttpResult deleteInner(String urlWithParam) {
    HttpDelete delete = new HttpDelete(urlWithParam);
    return doRequest(delete);
  }

  private HttpResult getInner(String urlWithParam) {
    HttpGet get = new HttpGet(urlWithParam);
    return doRequest(get);
  }

  private HttpResult putInner(String urlWithParam) {
    HttpPut put = new HttpPut(urlWithParam);
    return doRequest(put);
  }

  private HttpResult patchInner(String urlWithParam) {
    HttpPatch put = new HttpPatch(urlWithParam);
    return doRequest(put);
  }

  private HttpResult doRequest(HttpRequestBase request) {
    CloseableHttpResponse httpResponse = null;
    try {
      request.setHeaders(buildHeaders());
      request.setConfig(buildRequestConfig());
      setContentEntityIfPossible(request);
      httpResponse = httpClient.execute(request);
      HttpResult httpResult = new HttpResult(httpResponse, this);
      logRequest(request, httpResult);
      return httpResult;
    } catch (IOException e) {
      if (logMark) {
        log.error("http request exec failed. exp: {}", StackTraceUtil.getStackTrace2(e));
      }
      return new HttpResult(e, this);
    } finally {
      try {
        if (httpResponse != null) {
          httpResponse.close();
        }
      } catch (Exception e) {
        if (logMark) {
          log.error("http response close failed. exp:{}", StackTraceUtil.getStackTrace2(e));
        }
      } finally {
        request.releaseConnection();
      }
    }
  }

  private RequestConfig buildRequestConfig() {
    return RequestConfig.custom().setConnectTimeout(connTime).setSocketTimeout(readTime).build();
  }

  private String appendParamsToUrl() {
    String paramAppendix = buildParamPipeline(urlParams, multiParams);
    if (StringUtils.isEmpty(paramAppendix)) {
      return url;
    } else {
      String joiner = this.url.contains("?") ? "&" : "?";
      return this.url + joiner + paramAppendix;
    }
  }

  private String buildFormParam(ImmutableMap.Builder<String, String> rawParams) {
    ImmutableMap<String, String> params = buildParams(rawParams);
    params = urlEncode(params);
    if (params.isEmpty()) {
      return "";
    } else {
      return Joiner.on("&").withKeyValueSeparator("=").join(params);
    }
  }

  private String buildParamPipeline(ImmutableMap.Builder<String, String> rawParams,
                                    ImmutableMultimap.Builder<String, String> rawMultiParams) {
    ImmutableMap<String, String> params = buildParams(rawParams);
    params = signIfNecessary(params);
    params = urlEncode(params);

    ImmutableMultimap<String, String> multiParams = buildMultiParams(rawMultiParams);
    multiParams = urlEncode(multiParams);

    String res = "";
    if (!params.isEmpty()){
      res += Joiner.on("&").withKeyValueSeparator("=").join(params);
    }
    if (!multiParams.isEmpty()) {
      if (StringUtils.isNotEmpty(res)) {
        res += "&";
      }
      res += Joiner.on("&").withKeyValueSeparator("=").join(multiParams.entries());
    }
    return res;
  }

  private ImmutableMap<String, String> urlEncode(ImmutableMap<String, String> params) {
    ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
    params.forEach((key, value) -> {
      try {
        builder.put(key, URLEncoder.encode(value, StandardCharsets.UTF_8.name()));
      } catch (UnsupportedEncodingException e) {
        log.warn("encode value error: {}", StackTraceUtil.getStackTrace2(e));
      }
    });
    return builder.build();
  }

  private ImmutableMultimap<String, String> urlEncode(ImmutableMultimap<String, String> params) {
    ImmutableMultimap.Builder<String, String> builder = ImmutableMultimap.builder();
    params.entries().forEach(entry -> {
      try {
        builder.put(entry.getKey(), URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()));
      } catch (UnsupportedEncodingException e) {
        log.warn("encode value error: {}", StackTraceUtil.getStackTrace2(e));
      }
    });
    return builder.build();
  }

  private ImmutableMap<String, String> buildParams(ImmutableMap.Builder<String, String> params) {
    ImmutableMap<String, String> paramsMap = params.build();
    ImmutableMap.Builder<String, String> res = ImmutableMap.<String, String>builder().putAll(paramsMap);
    if (requestToRelay == null) {
      return res.build();
    }

    ImmutableSet<String> exclusion = urlParamExclusion.build();
    // 请求中指定的参数会覆盖relay中的参数。只exclude relay中的参数
    Set<String> keys = requestToRelay.getParameterMap().keySet();
    for (String key : keys) {
      if (!paramsMap.containsKey(key) && !exclusion.contains(key)) {
        res.put(key, requestToRelay.getParameter(key));
      }
    }

    return res.build();
  }

  private ImmutableMultimap<String, String> buildMultiParams(ImmutableMultimap.Builder<String, String> params) {
    ImmutableMultimap<String, String> paramsMap = params.build();
    ImmutableMultimap.Builder<String, String> res = ImmutableMultimap.<String, String>builder().putAll(paramsMap);
    if (requestToRelay == null) {
      return res.build();
    }

    // 请求中指定的参数会覆盖relay中的参数。
    Set<String> keys = requestToRelay.getParameterMap().keySet();
    for (String key : keys) {
      if (!paramsMap.containsKey(key)) {
        res.put(key, requestToRelay.getParameter(key));
      }
    }

    ImmutableSet<String> exclusion = urlParamExclusion.build();
    if (exclusion.isEmpty()) {
      return res.build();
    } else {
      ImmutableMultimap.Builder<String, String> builder = ImmutableMultimap.builder();
      res.build().entries().stream()
          .filter(entry -> !exclusion.contains(entry.getKey()))
          .forEach(builder::put);
      return builder.build();
    }
  }

  private void logRequest(HttpRequestBase request, HttpResult httpResult) {
    if (!logMark) {
      return;
    }
    try {
      if (!httpResult.isHttpSuccess()) {
         log.error("http url: {}, http status: {}, reqBody:{}, resBody:{}",
                     request.getURI().toString(),
                     httpResult.getHttpStatusCode(),
                     body,
                     httpResult.getResponseAsString());
      } else {
          log.info("http url: {}, http status: {}, reqBody:{}, resBody: {}"
              , request.getURI().toString()
              , httpResult.getHttpStatusCode()
              , body
              , httpResult.getResponseAsString());
      }
    } catch (Exception e) {
      log.error("http client log failed. exp:{}", StackTraceUtil.getStackTrace2(e));
    }
  }

  private Header[] buildHeaders() {
    ImmutableMap<String, String> header = headers.build();
    // relay headers in requestToRelay. in lower priority
    if (requestToRelay != null) {
      Enumeration<String> headerNameEnum = requestToRelay.getHeaderNames();
      while (headerNameEnum.hasMoreElements()) {
        String headerName = headerNameEnum.nextElement();
        String headerVal = requestToRelay.getHeader(headerName);
        if (!header.containsKey(headerName) && headerVal != null && !headsWontRelay.contains(headerName.toLowerCase())) {
          headers.put(headerName, headerVal);
        }
      }
      header = headers.build();
    }
    List<Header> headerList = new ArrayList<>();
    for (Map.Entry<String, String> entry : header.entrySet()) {
      headerList.add(new BasicHeader(entry.getKey(), entry.getValue()));
    }
    return headerList.toArray(new Header[header.size()]);
  }

  private ImmutableMap<String, String> signIfNecessary(ImmutableMap<String, String> params) {
    if (signer != null) {
      return signer.sign(params);
    } else {
      return params;
    }
  }

  public static HttpClientUtil start() {
    return new HttpClientUtil();
  }

  public static HttpClientUtil post(String url) {
    return start().method(RequestMethod.POST).url(url);
  }

  public static HttpClientUtil get(String url) {
    return start().method(RequestMethod.GET).url(url);
  }

  public static HttpClientUtil delete(String url) {
    return start().method(RequestMethod.DELETE).url(url);
  }

  public static HttpClientUtil put(String url) {
    return start().method(RequestMethod.PUT).url(url);
  }

  public static HttpClientUtil patch(String url) {
    return start().method(RequestMethod.PATCH).url(url);
  }

  public HttpClientUtil url(String url) {
    this.url = url;
    return this;
  }

  public HttpClientUtil param(String key, Object val) {
    urlParams.put(key, val == null ? "" : String.valueOf(val));
    return this;
  }

  public HttpClientUtil formParam(String key, Object val) {
    formParams.put(key, val == null ? "" : String.valueOf(val));
    return this;
  }

  public <T> HttpClientUtil multiParam(String key, Collection<T> values) {
    for (T value : values) {
      String valueStr = null == value ? "" : String.valueOf(value);
      multiParams.put(key, valueStr);
    }
    return this;
  }

  public HttpClientUtil body(String body) {
    this.body = body;
    return this;
  }

  public HttpClientUtil objectBody(Object jsonBody) {
    this.objectBody = jsonBody;
    return this;
  }

  public HttpClientUtil byteBody(byte[] byteBody) {
    this.byteBody = byteBody;
    return this;
  }

  public HttpClientUtil method(RequestMethod method) {
    this.method = method;
    return this;
  }

  public HttpClientUtil header(String key, String val) {
    headers.put(key, val);
    return this;
  }

  public HttpClientUtil contentType(ContentType contentType) {
    this.contentType = contentType;
    return this;
  }

  public HttpClientUtil connectionTimeout(int connTime) {
    this.connTime = connTime;
    return this;
  }

  public HttpClientUtil readTimeout(int readTime) {
    this.readTime = readTime;
    return this;
  }

  public HttpClientUtil muteLog() {
    this.logMark = false;
    return this;
  }

  public HttpClientUtil withSign(Signer sign) {
    this.signer = sign;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public String getBody() {
    return body;
  }

  public RequestMethod getMethod() {
    return method;
  }

  public ContentType getContentType() {
    return contentType;
  }

  public boolean shouldLog() {
    return logMark;
  }

  private void setContentEntityIfPossible(HttpRequestBase request) {
    if (!(request instanceof HttpEntityEnclosingRequest)) {
      return;
    }

    if (body != null) {
      setContentEntity(request, body, contentType);
    } else if (objectBody != null) {
      body = JSONObject.toJSONString(objectBody);
      contentType = ContentType.APPLICATION_JSON;
      setContentEntity(request, body, contentType);
    } else if (byteBody != null) {
      contentType = ContentType.APPLICATION_OCTET_STREAM;
      setByteEntity(request, byteBody, contentType);
    } else {
      body = buildFormParam(formParams);
      if (StringUtils.isNotBlank(body)) {
        contentType = ContentType.APPLICATION_FORM_URLENCODED;
        setContentEntity(request, body, contentType);
      }
    }
  }

  private void setByteEntity(HttpRequestBase request, byte[] byteBody, ContentType contentType) {
    ((HttpEntityEnclosingRequest) request).setEntity(new ByteArrayEntity(byteBody, contentType));
  }

  private void setContentEntity(HttpRequestBase request, String body, ContentType contentType) {
    ((HttpEntityEnclosingRequest) request).setEntity(new StringEntity(body, contentType));
  }

  public HttpClientUtil relay(HttpServletRequest request) {
    this.requestToRelay = request;
    return this;
  }

  public HttpClientUtil excludeParam(String key) {
    urlParamExclusion.add(key);
    return this;
  }
}
