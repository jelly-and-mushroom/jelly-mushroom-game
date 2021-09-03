package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

@RestController
@Slf4j
public class APIController {

  private MainService mainService;

  private ResourceService resourceService;

  private HttpTransferService httpTransferService;

  public APIController(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
    this.httpTransferService = httpTransferService;
  }

  @GetMapping("/full-moon/api/getGameData")
  public HttpResponseEntity getGameData() {
    try {
      JSONObject result = new JSONObject();
      result.put("bo", this.mainService.getGameEntity());
      result.put("vo", httpTransferService.convert(this.mainService.getGameEntity()));
      return HttpResponseEntity.success(result, null);
    } catch (Exception e) {
      String errorMsg = "api-getGameData执行时出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }

  @GetMapping("/full-moon/api/getResource")
  public HttpResponseEntity getResource() {
    try {
      JSONObject result = new JSONObject();
      result.put("cardList", this.resourceService.getServiceResourceEntity().getCardList());
      result.put("blessingList", this.resourceService.getServiceResourceEntity().getBlessingList());
      return HttpResponseEntity.success(result, null);
    } catch (Exception e) {
      String errorMsg = "api-getGameData执行时出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }
}
