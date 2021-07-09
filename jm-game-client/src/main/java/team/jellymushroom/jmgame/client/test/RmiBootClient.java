package team.jellymushroom.jmgame.client.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import team.jellymushroom.jmgame.core.server.rmi.IGameRmiService;

@Configuration
public class RmiBootClient {

  @Bean
  public RmiProxyFactoryBean rmiProxyFactoryBean(){
    RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
    rmiProxyFactoryBean.setServiceUrl("rmi://127.0.0.1:2002/" + IGameRmiService.RMI_SERVER_NAME);
    rmiProxyFactoryBean.setServiceInterface(IGameRmiService.class);
    return rmiProxyFactoryBean;

  }
}
