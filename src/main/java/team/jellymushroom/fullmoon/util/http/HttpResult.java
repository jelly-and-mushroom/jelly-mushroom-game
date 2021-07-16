package team.jellymushroom.fullmoon.util.http;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

@Slf4j
public class HttpResult {

  private Exception e;
  private CloseableHttpResponse httpResponse;
  private HttpClientUtil httpClientUtil;

  private String resBody;
  private byte[] resBytes;
  private JSONObject resBodyJson;
  private boolean isHttpSuccess = false;
  private Integer httpStatusCode;

  public HttpResult(CloseableHttpResponse httpResponse, HttpClientUtil httpClientUtil) {
    this.httpResponse = httpResponse;
    this.httpClientUtil = httpClientUtil;
    init();
  }

  public HttpResult(Exception e, HttpClientUtil httpClientUtil) {
    this.e = e;
    this.httpClientUtil = httpClientUtil;
    init();
  }

  public HttpResult(IOException e, HttpClientUtil httpClientUtil) {
    this.httpClientUtil = httpClientUtil;
    this.e = e;
    init();
  }

  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }

  public boolean isHttpSuccess() {
    return isHttpSuccess;
  }

  private void init() {
    if (e != null) {
      isHttpSuccess = false;
      return;
    }

    if (httpResponse == null) {
      isHttpSuccess = false;
      return;
    }

    if (httpResponse.getStatusLine() == null) {
      isHttpSuccess = false;
      return;
    }

    httpStatusCode = httpResponse.getStatusLine().getStatusCode();
    isHttpSuccess = httpStatusCode / 10 == 20;

    try {
      resBytes = EntityUtils.toByteArray(httpResponse.getEntity());
      resBody = new String(resBytes, Charsets.UTF_8);
    } catch (Exception e) {
      if (httpClientUtil.shouldLog()) {
        log.error("convert response entity to string fialed. res:{}.", resBody, e);
      }
      isHttpSuccess = false;
    }
  }

  public boolean isSuccess(String errorCodeFieldName) {
    return isSuccess(errorCodeFieldName, "0");
  }

  public boolean isSuccess(String errCodeFieldPath, String successVal) {
    try {
      if (successVal == null) {
        throw new IllegalArgumentException("successVal is null");
      }

      if (!isHttpSuccess) {
        return false;
      }
      if (!resBody.trim().startsWith("{")) {
        throw new IllegalStateException("not valid json response");
      }

      if (resBodyJson == null) {
        resBodyJson = JSONObject.parseObject(resBody, Feature.OrderedField);
      }
      String fieldVal = getString(errCodeFieldPath);
      return successVal.equals(fieldVal);
    } catch (Exception e) {
      if (httpClientUtil.shouldLog()) {
        log.error("convert response entity to string fialed. res:{}. ", resBody, e);
      }
      return false;
    }
  }

  public String getResponseAsString() {
    return resBody;
  }

  public <T> T getResponse(String path, Class<T> returnType) {
    return doGetResponse(path, returnType);
  }

  public <T> List<T> getListResponse(String path, Class<T> returnType) {
    if (resBody == null) {
      return null;
    }

    if (resBodyJson == null) {
      resBodyJson = JSONObject.parseObject(resBody, Feature.OrderedField);
    }

    Object res = resolveByPath(resBodyJson, path);
    if (res instanceof JSONArray) {
      return ((JSONArray) res).toJavaList(returnType);
    } else {
      String json = JSONObject.toJSONString(res);
      return JSONArray.parseArray(json, returnType);
    }
  }

  public <T> T getResponse(String path, TypeReference returnType) {
    return doGetResponse(path, returnType);
  }

  @SuppressWarnings("unchecked")
  private <T> T doGetResponse(String path, Object returnType) {
    if (resBody == null) {
      return null;
    }

    if (resBodyJson == null) {
      resBodyJson = JSONObject.parseObject(resBody, Feature.OrderedField);
    }

    if (returnType instanceof Class &&
        JSON.class.isAssignableFrom((Class)returnType)) {
      return (T) resolveByPath(resBodyJson, path);
    }

    if (returnType instanceof TypeReference) {
      JSON part = (JSON) resolveByPath(resBodyJson, path);
      if (part == null) {
        return null;
      }
      String json = part.toJSONString();
      return (T) JSONObject.parseObject(json, (TypeReference) returnType, Feature.OrderedField);
    }

    if (returnType instanceof Class) {
      return (T) ((JSON) resolveByPath(resBodyJson, path)).toJavaObject((Class) returnType);
    }

    throw new RuntimeException("不支持的返回值类型：" + returnType);
  }

  private Object resolveByPath(JSONObject jsonObject, String path) {
    if (StringUtils.isEmpty(path)) {
      return jsonObject;
    } else {
      if (!path.startsWith("$.")) {
        path = "$." + path;
      }
      return JSONPath.eval(jsonObject, path);
    }
  }

  public String buildErrorMsg() {
    return "httpStatusCode:" + httpStatusCode + ", resBody:" + resBody;
  }

  public RuntimeException buildException(String extraErrorMsg) {
    String errorMsg = extraErrorMsg + "," + buildErrorMsg();

    if ( e != null) {
      return new RuntimeException(errorMsg + ", " + e.getMessage(), e);
    } else {
      return new RuntimeException(errorMsg);
    }
  }

  /**
   * 通过path访问某Object字段。
   * path以点分隔如： data.key
   * 暂不支持数组寻址 如：data.key[2]
   */
  public JSONObject getJSONObject(String path) {
    return getResponse(path, JSONObject.class);
  }

  public String getString(String path) {
    if (resBodyJson == null) {
      resBodyJson = JSONObject.parseObject(resBody, Feature.OrderedField);
    }
    return String.valueOf(resolveByPath(resBodyJson, path));
  }

  public Number getNumber(String path) {
    if (resBodyJson == null) {
      resBodyJson = JSONObject.parseObject(resBody, Feature.OrderedField);
    }
    return (Number) resolveByPath(resBodyJson, path);
  }

  /**
   * 请使用jsonPath
   * 通过path访问某Array字段。
   * path以点分隔如： data.key
   */
  public JSONArray getJSONArray(String path) {
    return getResponse(path, JSONArray.class);
  }

  public byte[] getResponseAsBytes() {
    return resBytes;
  }
}