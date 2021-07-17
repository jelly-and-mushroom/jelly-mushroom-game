package team.jellymushroom.fullmoon.runnable;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import team.jellymushroom.fullmoon.constant.HttpResponseStatusEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.http.HttpGameEntity;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.entity.http.HttpServerControlEntity;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.util.http.HttpClientUtil;
import team.jellymushroom.fullmoon.util.http.HttpResult;

@Slf4j
public class HttpUpdateGameRunnable implements Runnable {

  private MainService mainService;

  private HttpServerControlEntity serverControl;

  private GameEntity game;

  public HttpUpdateGameRunnable(MainService mainService, HttpServerControlEntity serverControl, GameEntity game) {
    this.mainService = mainService;
    this.serverControl = serverControl;
    this.game = game;
  }

  @SneakyThrows
  @Override
  public void run() {
    if (ServerControlEntity.getInstance().getHttpSendWait()) {
      log.info("尚有未完成的http通信，不进行服务端向客户端数据同步");
      return;
    }
    ServerControlEntity.getInstance().setHttpSendWait(true);
    while (true) {
      HttpGameEntity body = new HttpGameEntity();
      body.setServerControl(this.serverControl);
      body.setGame(this.game);
      HttpResult httpResult = HttpClientUtil
          .post(this.mainService.getHttpOpponentHost() + "/full-moon/updateGame")
          .objectBody(body).exec();
      if (!httpResult.isHttpSuccess()) {
        log.info("服务端向客户端数据同步失败,等待{}ms后重试", MainService.HTTP_RETRY_INTERVAL);
        Thread.sleep(MainService.HTTP_RETRY_INTERVAL);
        continue;
      }
      String status = httpResult.getString(HttpResponseEntity.STATUS_KEY);
      if (!HttpResponseStatusEnum.SUCCESS.getValue().equals(status)) {
        log.error("客户端返回异常,msg:{},等待{}ms后重试", httpResult.getString(HttpResponseEntity.MSG_KEY), MainService.HTTP_RETRY_INTERVAL);
        Thread.sleep(MainService.HTTP_RETRY_INTERVAL);
        continue;
      }
      log.info("服务端向客户端数据同步成功");
      break;
    }
    ServerControlEntity.getInstance().setHttpSendWait(false);
  }
}
