package team.jellymushroom.fullmoon.runnable;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.util.http.HttpClientUtil;
import team.jellymushroom.fullmoon.util.http.HttpResult;

@Slf4j
public class WaitConnectRunnable implements Runnable {

  private MainService mainService;

  public WaitConnectRunnable(MainService mainService) {
    this.mainService = mainService;
  }

  @SneakyThrows
  @Override
  public void run() {
    while (true) {
      HttpResult httpResult = HttpClientUtil.get(this.mainService.getHttpOpponentHost() + "/full-moon/getHttpWaitConnectInfo").exec();
      if (!httpResult.isHttpSuccess()) {
        log.info("失败");
        Thread.sleep(1000);
      } else {
        log.info("成功");
        break;
      }
    }
  }
}
