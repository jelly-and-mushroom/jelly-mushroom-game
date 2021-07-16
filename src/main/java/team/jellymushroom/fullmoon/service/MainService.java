package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.runnable.WaitConnectRunnable;

import javax.annotation.PostConstruct;

@Service
public class MainService {

  @Getter
  private GameEntity gameEntity;

  @Getter
  private ServerControlEntity serverControlEntity = new ServerControlEntity();

  @Value("${fm.http.opponent.host}")
  @Getter
  private String httpOpponentHost;

  private ResourceService resourceService;

  public MainService(ResourceService resourceService) {
    this.resourceService = resourceService;
    this.serverControlEntity.setCurrentChooseRole(this.resourceService.getServiceResourceEntity().getGameRoleMap().get(0));
  }

  @PostConstruct
  public void init() {
    new Thread(new WaitConnectRunnable(this)).start();
  }

  public PlayerEntity getPlayerMyself() {
    return this.serverControlEntity.getIsServer() ? this.gameEntity.getServerPlayer() : this.gameEntity.getClientPlayer();
  }

  public PlayerEntity getPlayerOpponent() {
    return this.serverControlEntity.getIsServer() ? this.gameEntity.getClientPlayer() : this.gameEntity.getServerPlayer();
  }
}
