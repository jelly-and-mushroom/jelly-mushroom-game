package team.jellymushroom.fullmoon.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.entity.http.HttpWaitConnectEntity;
import team.jellymushroom.fullmoon.service.MainService;

@RestController
@Slf4j
public class MainController {

  @Getter
  private MainService mainService;

  public MainController(MainService mainService) {
    this.mainService = mainService;
  }

  @GetMapping("/full-moon/getHttpWaitConnectInfo")
  public HttpResponseEntity getHttpWaitConnectInfo() {
    try {
      HttpWaitConnectEntity result = new HttpWaitConnectEntity();
      result.setInitTime(ServerControlEntity.getInstance().getInitTime());
      return HttpResponseEntity.success(result, null);
    } catch (Exception e) {
      String errorMsg = "getHttpWaitConnectInfo执行时出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }

  @PostMapping("/full-moon/updateGame")
  @ResponseBody
  public HttpResponseEntity updateGame(@RequestBody GameEntity gameEntity) {
    try {
      return HttpResponseEntity.success(null, null);
    } catch (Exception e) {
      String errorMsg = "updateGame执行时出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }
}
