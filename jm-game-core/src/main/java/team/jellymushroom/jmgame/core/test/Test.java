package team.jellymushroom.jmgame.core.test;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Test {

  @PostConstruct
  public void init() {
    System.out.println("a");
  }
}
