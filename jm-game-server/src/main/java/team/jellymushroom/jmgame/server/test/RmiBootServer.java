package team.jellymushroom.jmgame.server.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import team.jellymushroom.jmgame.core.test.IUserService;
import team.jellymushroom.jmgame.core.test.User;

import java.rmi.RemoteException;

@Configuration
public class RmiBootServer {

  private final IUserService userService;

  public RmiBootServer(IUserService userService) {
    this.userService = userService;
  }

  @Bean
  public RmiServiceExporter rmiServiceExporter(){
    new User();
    RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
    rmiServiceExporter.setServiceName("userService");
    rmiServiceExporter.setService(userService);
    rmiServiceExporter.setServiceInterface(IUserService.class);
    rmiServiceExporter.setRegistryPort(2002);// 默认为1099，注意占用问题
    try {
      rmiServiceExporter.afterPropertiesSet();
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    return rmiServiceExporter;
  }
}
