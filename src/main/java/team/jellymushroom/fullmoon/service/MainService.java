package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.runnable.HttpWaitConnectRunnable;

import javax.annotation.PostConstruct;

@Service
public class MainService {

  @Getter
  @Setter
  private GameEntity gameEntity = new GameEntity();

  @Getter
  @Setter
  private Boolean isServer;

  @Getter
  private Long initTime = System.currentTimeMillis();

  @Value("${fm.save.load}")
  private Boolean loadSave;

  private HttpTransferService httpTransferService;

  private ResourceService resourceService;

  public MainService(HttpTransferService httpTransferService, ResourceService resourceService) {
    this.httpTransferService = httpTransferService;
    this.resourceService = resourceService;
  }

  @PostConstruct
  public void init() {
    new Thread(new HttpWaitConnectRunnable(this, this.httpTransferService, this.resourceService)).start();
  }

  public PlayerEntity getPlayerMyself() {
    return this.isServer ? this.gameEntity.getServerPlayer() : this.gameEntity.getClientPlayer();
  }

  public PlayerEntity getPlayerOpponent() {
    return this.isServer ? this.gameEntity.getClientPlayer() : this.gameEntity.getServerPlayer();
  }

  public Long getServerPriority() {
    if (this.loadSave) {
      return 0L;
    }
    return this.initTime;
  }
}
