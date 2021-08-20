package team.jellymushroom.fullmoon.runnable;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import team.jellymushroom.fullmoon.constant.HttpResponseStatusEnum;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.entity.http.HttpWaitConnectEntity;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;
import team.jellymushroom.fullmoon.util.http.HttpClientUtil;
import team.jellymushroom.fullmoon.util.http.HttpResult;

@Slf4j
public class HttpWaitConnectRunnable implements Runnable {

  private MainService mainService;

  private HttpTransferService httpTransferService;

  private ResourceService resourceService;

  public HttpWaitConnectRunnable(MainService mainService, HttpTransferService httpTransferService, ResourceService resourceService) {
    this.mainService = mainService;
    this.httpTransferService = httpTransferService;
    this.resourceService = resourceService;
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
      Long remoteServerPriority = httpWaitConnectEntity.getServerPriority();
      Long localServerPriority = this.mainService.getServerPriority();
      if (remoteServerPriority.equals(localServerPriority)) {
        log.info("联机双方均试图读取存档，成为服务端。导致连接失败");
        continue;
      }
      if (remoteServerPriority < localServerPriority) {
        log.info("连接对手成功.remoteServerPriority:{},localServerPriority:{}.计划成为客户端，等待服务端同步初始数据", remoteServerPriority, localServerPriority);
        return;
      }
      log.info("连接对手成功.remoteServerPriority:{},localServerPriority:{}.成为服务端", remoteServerPriority, localServerPriority);
      if (localServerPriority.longValue() == 0) {
        this.mainService.setGameEntity(this.httpTransferService.convert(this.resourceService.load()));
        log.info("读取存档成功");
      }
      this.mainService.setIsServer(true);
      new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
      return;
    }
  }
}
