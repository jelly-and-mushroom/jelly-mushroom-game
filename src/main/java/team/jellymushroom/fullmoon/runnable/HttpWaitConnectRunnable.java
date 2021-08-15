package team.jellymushroom.fullmoon.runnable;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import team.jellymushroom.fullmoon.constant.HttpResponseStatusEnum;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.entity.http.HttpWaitConnectEntity;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.util.http.HttpClientUtil;
import team.jellymushroom.fullmoon.util.http.HttpResult;

@Slf4j
public class HttpWaitConnectRunnable implements Runnable {

  private MainService mainService;

  private HttpTransferService httpTransferService;

  public HttpWaitConnectRunnable(MainService mainService, HttpTransferService httpTransferService) {
    this.mainService = mainService;
    this.httpTransferService = httpTransferService;
  }

  @SneakyThrows
  @Override
  public void run() {
    while (true) {
      HttpResult httpResult = HttpClientUtil.get(this.httpTransferService.getHttpOpponentHost() + "/full-moon/getHttpWaitConnectInfo").exec();
      if (!httpResult.isHttpSuccess()) {
        log.info("连接对手: {} 失败,等待{}ms后重试", this.httpTransferService.getHttpOpponentHost(), HttpTransferService.HTTP_RETRY_INTERVAL);
        Thread.sleep(HttpTransferService.HTTP_RETRY_INTERVAL);
        continue;
      }
      String status = httpResult.getString(HttpResponseEntity.STATUS_KEY);
      if (!HttpResponseStatusEnum.SUCCESS.getValue().equals(status)) {
        log.error("对手服务返回异常,msg:{},等待{}ms后重试", httpResult.getString(HttpResponseEntity.MSG_KEY), HttpTransferService.HTTP_RETRY_INTERVAL);
        Thread.sleep(HttpTransferService.HTTP_RETRY_INTERVAL);
        continue;
      }
      JSONObject data = httpResult.getJSONObject(HttpResponseEntity.DATA_KEY);
      HttpWaitConnectEntity httpWaitConnectEntity = JSONObject.parseObject(data.toJSONString(), HttpWaitConnectEntity.class);
      if (null != httpWaitConnectEntity.getIsServer()) {
        this.mainService.setIsServer(!httpWaitConnectEntity.getIsServer());
        this.mainService.setGameEntity(this.httpTransferService.convert(httpWaitConnectEntity.getHttpGame()));
        log.info("连接对手成功，成为:{}", this.mainService.getIsServer() ? "服务端" : "客户端");
        return;
      }
      Long remoteInitTime = httpWaitConnectEntity.getInitTime();
      Long localInitTime = this.mainService.getInitTime();
      if (remoteInitTime < localInitTime) {
        log.info("连接对手成功.remoteInitTime:{},localInitTime:{}.成为客户端", remoteInitTime, localInitTime);
        this.mainService.setIsServer(false);
        return;
      }
      log.info("连接对手成功.remoteInitTime:{},localInitTime:{}.成为服务端", remoteInitTime, localInitTime);
      this.mainService.setIsServer(true);
      return;
    }
  }
}
