package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;

/**
 * TODO
 */
@Controller
@Slf4j
public class TestController {

  @PostMapping("/test")
  @ResponseBody
  public HttpResponseEntity generateDimRoleImage(@RequestBody JSONObject requestParam) {
    try {
      return HttpResponseEntity.success(null, null);
    } catch (Exception e) {
      return HttpResponseEntity.error("");
    }
  }
}
