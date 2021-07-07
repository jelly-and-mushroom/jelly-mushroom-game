package team.jellymushroom.jmgame.server.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.jellymushroom.jmgame.core.test.Test;

@Configuration
public class TestServer {

  @Bean
  public Test buildTest() {
    return new Test();
  }
}
