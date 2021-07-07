package team.jellymushroom.jmgame.client.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.jellymushroom.jmgame.core.test.IUserService;

import javax.annotation.PostConstruct;

@Component
public class TestResult {

  @Autowired
  private IUserService userService;

  @PostConstruct
  public void init() {
    System.out.println(userService.getUserByName("admin"));
  }
}
