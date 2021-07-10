package team.jellymushroom.fullmoon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class TestController {

  @GetMapping("/test")
  @ResponseBody
  public String getArticleList() {
    log.info("收到test请求");
    return "测试成功";
  }
}
