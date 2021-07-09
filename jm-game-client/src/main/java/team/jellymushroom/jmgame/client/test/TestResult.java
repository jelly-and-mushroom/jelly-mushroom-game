package team.jellymushroom.jmgame.client.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import team.jellymushroom.jmgame.core.server.rmi.IGameRMIService;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class TestResult {

  private final IGameRMIService gameRMIService;

  public TestResult(IGameRMIService gameRMIService) {
    this.gameRMIService = gameRMIService;
  }

  @PostConstruct
  public void init() {
    log.info(gameRMIService.test(1));
  }
}
