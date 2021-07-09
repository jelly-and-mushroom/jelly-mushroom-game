package team.jellymushroom.jmgame.server.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import team.jellymushroom.jmgame.core.server.rmi.IGameRMIService;

import java.rmi.RemoteException;

@Configuration
public class RmiBootServer {

  private final IGameRMIService gameRMIService;

  public RmiBootServer(IGameRMIService gameRMIService) {
    this.gameRMIService = gameRMIService;
  }

  @Bean
  public RmiServiceExporter rmiServiceExporter(){
    RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
    rmiServiceExporter.setServiceName("gameRMIService");
    rmiServiceExporter.setService(gameRMIService);
    rmiServiceExporter.setServiceInterface(IGameRMIService.class);
    rmiServiceExporter.setRegistryPort(2002);// 默认为1099，注意占用问题
    try {
      rmiServiceExporter.afterPropertiesSet();
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    return rmiServiceExporter;
  }
}
