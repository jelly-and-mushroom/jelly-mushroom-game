package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

@RestController
@Slf4j
public class TestController {

  private MainService mainService;

  private ResourceService resourceService;

  public TestController(MainService mainService, ResourceService resourceService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
  }

  @GetMapping("/full-moon/test")
  public HttpResponseEntity test() {
    JSONObject result = new JSONObject();
    return HttpResponseEntity.success(result, null);
  }
}
