package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.http.*;
import team.jellymushroom.fullmoon.service.KeyEventService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

import java.util.Map;

@RestController
@Slf4j
public class MainController {

  private MainService mainService;

  private KeyEventService keyEventService;

  private ResourceService resourceService;

  public MainController(MainService mainService, KeyEventService keyEventService, ResourceService resourceService) {
    this.mainService = mainService;
    this.keyEventService = keyEventService;
    this.resourceService = resourceService;
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
  public HttpResponseEntity updateGame(@RequestBody JSONObject jsonObject) {
    try {
      Boolean isServer = ServerControlEntity.getInstance().getIsServer();
      if (null != isServer && isServer) {
        String errorMsg = "updateGame接口无效，不应被调用";
        log.error(errorMsg);
        return HttpResponseEntity.error(errorMsg);
      }
      log.info("接收到服务端更新的数据:{}", jsonObject.toJSONString());
      HttpGameEntity httpGameEntity = JSONObject.parseObject(jsonObject.toJSONString(), HttpGameEntity.class);
      if (null != httpGameEntity.getGame()) {
        this.mainService.setGameEntity(httpGameEntity.getGame());
      }
      HttpServerControlEntity serverControl = httpGameEntity.getServerControl();
      Map<Integer, GameRoleEntity> gameRoleMap = this.resourceService.getServiceResourceEntity().getGameRoleMap();
      if (null != serverControl) {
        if (null != serverControl.getCurrentChooseRoleIndex()) {
          ServerControlEntity.getInstance().setCurrentChooseRole(gameRoleMap.get(serverControl.getCurrentChooseRoleIndex()));
        }
        if (null != serverControl.getOpponentCurrentChooseRoleIndex()) {
          ServerControlEntity.getInstance().setOpponentCurrentChooseRole(gameRoleMap.get(serverControl.getOpponentCurrentChooseRoleIndex()));
        }
      }
      ServerControlEntity.getInstance().setIsServer(false);
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
      Boolean isServer = ServerControlEntity.getInstance().getIsServer();
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
