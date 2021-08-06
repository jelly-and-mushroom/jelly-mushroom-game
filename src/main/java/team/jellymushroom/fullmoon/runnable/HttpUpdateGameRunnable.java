package team.jellymushroom.fullmoon.runnable;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import team.jellymushroom.fullmoon.constant.HttpResponseStatusEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.http.HttpDataEntity;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.entity.http.HttpServerControlEntity;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.util.http.HttpClientUtil;
import team.jellymushroom.fullmoon.util.http.HttpResult;

@Slf4j
public class HttpUpdateGameRunnable implements Runnable {

  private HttpTransferService httpTransferService;

  private HttpServerControlEntity serverControl;

  private GameEntity game;

  public HttpUpdateGameRunnable(HttpTransferService httpTransferService, HttpServerControlEntity serverControl, GameEntity game) {
    this.httpTransferService = httpTransferService;
    this.serverControl = serverControl;
    this.game = game;
  }

  @SneakyThrows
  @Override
  public void run() {
    this.httpTransferService.setHttpSendWait(true);
    while (true) {
      HttpDataEntity body = new HttpDataEntity();
      body.setServerControl(this.serverControl);
      body.setGame(this.httpTransferService.convert(this.game));
      HttpResult httpResult = HttpClientUtil
          .post(this.httpTransferService.getHttpOpponentHost() + "/full-moon/updateGame")
          .objectBody(body).exec();
      if (!httpResult.isHttpSuccess()) {
        log.info("服务端向客户端数据同步失败,等待{}ms后重试", HttpTransferService.HTTP_RETRY_INTERVAL);
        Thread.sleep(HttpTransferService.HTTP_RETRY_INTERVAL);
        continue;
      }
      String status = httpResult.getString(HttpResponseEntity.STATUS_KEY);
      if (!HttpResponseStatusEnum.SUCCESS.getValue().equals(status)) {
        log.error("客户端返回异常,msg:{},等待{}ms后重试", httpResult.getString(HttpResponseEntity.MSG_KEY), HttpTransferService.HTTP_RETRY_INTERVAL);
        Thread.sleep(HttpTransferService.HTTP_RETRY_INTERVAL);
        continue;
      }
      log.info("服务端向客户端数据同步成功");
      break;
    }
    this.httpTransferService.setHttpSendWait(false);
  }
}
