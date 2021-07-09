package team.jellymushroom.jmgame.client.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import team.jellymushroom.jmgame.core.server.rmi.IGameRmiService;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class TestResult {

  private final IGameRmiService gameRmiService;

  public TestResult(IGameRmiService gameRmiService) {
    this.gameRmiService = gameRmiService;
  }

  @PostConstruct
  public void init() {
    log.info(gameRmiService.test(1));
  }
}
