package team.jellymushroom.fullmoon.controller;

 import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

import java.util.AbstractMap;
 import java.util.Map;

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
    Map.Entry<String, Integer>  entry = new AbstractMap.SimpleEntry("123", 13);
    return HttpResponseEntity.success(result, null);
  }
}
