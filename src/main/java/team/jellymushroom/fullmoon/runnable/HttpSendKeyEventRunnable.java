package team.jellymushroom.fullmoon.runnable;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import team.jellymushroom.fullmoon.constant.HttpResponseStatusEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.http.HttpKeyEventEntity;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.util.http.HttpClientUtil;
import team.jellymushroom.fullmoon.util.http.HttpResult;

@Slf4j
public class HttpSendKeyEventRunnable implements Runnable {

  private MainService mainService;

  private Integer keyCode;

  public HttpSendKeyEventRunnable(MainService mainService, Integer keyCode) {
    this.mainService = mainService;
    this.keyCode = keyCode;
  }

  @SneakyThrows
  @Override
  public void run() {
    if (ServerControlEntity.getInstance().getHttpSendWait()) {
      log.info("尚有未完成的http通信，不接受新的客户端按键输入,keyCode:{}", keyCode);
      return;
    }
    ServerControlEntity.getInstance().setHttpSendWait(true);
    while (true) {
      HttpKeyEventEntity body = new HttpKeyEventEntity();
      body.setKeyCode(this.keyCode);
      HttpResult httpResult = HttpClientUtil
          .post(this.mainService.getHttpOpponentHost() + "/full-moon/receiveKeyEvent")
          .objectBody(body).exec();
      if (!httpResult.isHttpSuccess()) {
        log.info("客户端发送指令 {} 给服务端失败,等待{}ms后重试", this.keyCode, MainService.HTTP_RETRY_INTERVAL);
        Thread.sleep(MainService.HTTP_RETRY_INTERVAL);
        continue;
      }
      String status = httpResult.getString(HttpResponseEntity.STATUS_KEY);
      if (!HttpResponseStatusEnum.SUCCESS.getValue().equals(status)) {
        log.error("服务端返回异常,msg:{},等待{}ms后重试", httpResult.getString(HttpResponseEntity.MSG_KEY), MainService.HTTP_RETRY_INTERVAL);
        Thread.sleep(MainService.HTTP_RETRY_INTERVAL);
        continue;
      }
      log.info("客户端发送指令 {} 给服务端成功", this.keyCode);
      break;
    }
    ServerControlEntity.getInstance().setHttpSendWait(false);
  }
}
