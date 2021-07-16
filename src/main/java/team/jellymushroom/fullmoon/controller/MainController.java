package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    log.info("getHttpWaitConnectInfo接到http请求");
    try {
      HttpWaitConnectEntity result = new HttpWaitConnectEntity();
      result.setInitTime(this.mainService.getServerControlEntity().getInitTime());
      return HttpResponseEntity.success(result, null);
    } catch (Exception e) {
      String errorMsg = "getHttpWaitConnectInfo执行时出错出错";
      log.error(errorMsg, e);
      return HttpResponseEntity.error(errorMsg + ":" + e.getMessage());
    }
  }

  @PostMapping("/test")
  @ResponseBody
  public HttpResponseEntity test(@RequestBody JSONObject requestParam) {
    try {
      return HttpResponseEntity.success(null, null);
    } catch (Exception e) {
      return HttpResponseEntity.error("");
    }
  }
}
