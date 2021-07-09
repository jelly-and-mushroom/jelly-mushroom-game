package team.jellymushroom.jmgame.server.rmi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.jmgame.core.server.rmi.IGameRMIService;

@Slf4j
@Service
public class GameRMIServiceImpl implements IGameRMIService {

  @Override
  public String test(Integer testParam) {
    log.info("test方法收到请求");
    return "test方法返回的结果";
  }
}
