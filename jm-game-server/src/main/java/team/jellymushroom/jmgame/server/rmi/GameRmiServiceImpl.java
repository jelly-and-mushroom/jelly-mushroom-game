package team.jellymushroom.jmgame.server.rmi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.jmgame.core.server.rmi.IGameRmiService;

@Slf4j
@Service
public class GameRmiServiceImpl implements IGameRmiService {

  @Override
  public String test(Integer testParam) {
    log.info("test方法收到请求");
    return "test方法返回的结果";
  }
}
