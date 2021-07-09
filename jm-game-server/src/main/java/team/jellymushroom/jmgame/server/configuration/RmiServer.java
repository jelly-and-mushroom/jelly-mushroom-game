package team.jellymushroom.jmgame.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import team.jellymushroom.jmgame.core.server.rmi.IGameRmiService;

import java.rmi.RemoteException;

@Configuration
public class RmiServer {

  private final IGameRmiService gameRmiService;

  public RmiServer(IGameRmiService gameRmiService) {
    this.gameRmiService = gameRmiService;
  }

  @Bean
  public RmiServiceExporter rmiServiceExporter() {
    RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
    rmiServiceExporter.setServiceName(IGameRmiService.RMI_SERVER_NAME);
    rmiServiceExporter.setService(gameRmiService);
    rmiServiceExporter.setServiceInterface(IGameRmiService.class);
    rmiServiceExporter.setRegistryPort(2002);
    try {
      rmiServiceExporter.afterPropertiesSet();
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    return rmiServiceExporter;
  }
}
