package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

@RestController
@Slf4j
public class APIController {

  private MainService mainService;

  private ResourceService resourceService;

  public APIController(MainService mainService, ResourceService resourceService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
  }

  @GetMapping("/full-moon/api/getGameData")
  public HttpResponseEntity getGameData() {
    try {
      JSONObject result = new JSONObject();
      result.put("game", this.mainService.getGameEntity());
      result.put("serverControl", ServerControlEntity.getInstance());
      return HttpResponseEntity.success(result, null);
    } catch (Exception e) {
      String errorMsg = "api-getGameData执行时出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }

  @GetMapping("/full-moon/api/getCard")
  public HttpResponseEntity getCard() {
    try {
      JSONObject result = new JSONObject();
      result.put("cardList", this.resourceService.getServiceResourceEntity().getCardList());
      return HttpResponseEntity.success(result, null);
    } catch (Exception e) {
      String errorMsg = "api-getGameData执行时出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }
}
