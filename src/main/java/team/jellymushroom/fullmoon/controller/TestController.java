package team.jellymushroom.fullmoon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;

@RestController
@Slf4j
public class TestController {

  @GetMapping("/full-moon/test")
  public HttpResponseEntity test() {
    return HttpResponseEntity.success("success", null);
  }
}
