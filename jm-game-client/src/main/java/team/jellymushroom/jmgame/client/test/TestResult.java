package team.jellymushroom.jmgame.client.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.jellymushroom.jmgame.core.test.IUserService;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class TestResult {

  @Autowired
  private IUserService userService;

  @PostConstruct
  public void init() {
    log.info(userService.getUserByName("admin").toString());
  }
}
