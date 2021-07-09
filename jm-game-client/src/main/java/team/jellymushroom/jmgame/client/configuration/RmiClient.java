package team.jellymushroom.jmgame.client.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import team.jellymushroom.jmgame.core.server.rmi.IGameRmiService;

@Configuration
@Slf4j
public class RmiClient {

  @Value(value = "${jm.rmi.server.ip}")
  private String serverIp;

  @Value(value = "${jm.rmi.port}")
  private Integer port;

  @Bean
  public RmiProxyFactoryBean rmiProxyFactoryBean() {
    RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
    rmiProxyFactoryBean.setServiceUrl("rmi://" + serverIp + ":" + port + "/" + IGameRmiService.RMI_SERVER_NAME);
    rmiProxyFactoryBean.setServiceInterface(IGameRmiService.class);
    log.info("rmi client 初始化完成.serverIp:{},port:{}", serverIp, port);
    return rmiProxyFactoryBean;
  }
}
