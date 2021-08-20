package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import team.jellymushroom.fullmoon.entity.http.HttpGameEntity;
import team.jellymushroom.fullmoon.entity.http.HttpKeyEventEntity;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.entity.http.HttpWaitConnectEntity;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.KeyEventService;
import team.jellymushroom.fullmoon.service.MainService;

@RestController
@Slf4j
public class MainController {

  private MainService mainService;

  private HttpTransferService httpTransferService;

  private KeyEventService keyEventService;

  public MainController(MainService mainService, KeyEventService keyEventService, HttpTransferService httpTransferService) {
    this.mainService = mainService;
    this.keyEventService = keyEventService;
    this.httpTransferService = httpTransferService;
  }

  @GetMapping("/full-moon/getHttpWaitConnectInfo")
  public HttpResponseEntity getHttpWaitConnectInfo() {
    try {
      HttpWaitConnectEntity result = new HttpWaitConnectEntity();
      result.setInitTime(this.mainService.getInitTime());
      return HttpResponseEntity.success(result, null);
    } catch (Exception e) {
      String errorMsg = "getHttpWaitConnectInfo执行时出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }

  @PostMapping("/full-moon/updateGame")
  @ResponseBody
  public HttpResponseEntity updateGame(@RequestBody JSONObject jsonObject) {
    try {
      Boolean isServer = this.mainService.getIsServer();
      if (null != isServer && isServer) {
        String errorMsg = "updateGame接口无效，不应被调用";
        log.error(errorMsg);
        return HttpResponseEntity.error(errorMsg);
      }
      log.info("接收到服务端更新的数据:{}", jsonObject.toJSONString());
      HttpGameEntity httpGame = JSONObject.parseObject(jsonObject.toJSONString(), HttpGameEntity.class);
      this.mainService.setGameEntity(this.httpTransferService.convert(httpGame));
      this.mainService.setIsServer(false);
      return HttpResponseEntity.success(null, null);
    } catch (Exception e) {
      String errorMsg = "updateGame执行时出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }

  @PostMapping("/full-moon/receiveKeyEvent")
  @ResponseBody
  public HttpResponseEntity receiveKeyEvent(@RequestBody HttpKeyEventEntity httpKeyEventEntity) {
    try {
      Boolean isServer = this.mainService.getIsServer();
      if (null == isServer || !isServer) {
        String errorMsg = "receiveKeyEvent接口无效，不应被调用";
        log.error(errorMsg);
        return HttpResponseEntity.error(errorMsg);
      }
      this.keyEventService.keyPressed(httpKeyEventEntity.getKeyCode(), false);
      return HttpResponseEntity.success(null, null);
    } catch (Exception e) {
      String errorMsg = "updateGame执行时出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }
}
