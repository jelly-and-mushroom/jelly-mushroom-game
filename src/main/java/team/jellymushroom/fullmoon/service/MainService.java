package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import lombok.Setter;
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

  private HttpTransferService httpTransferService;

  public MainService(HttpTransferService httpTransferService) {
    this.httpTransferService = httpTransferService;
  }

  @PostConstruct
  public void init() {
    new Thread(new HttpWaitConnectRunnable(this, this.httpTransferService)).start();
  }

  public PlayerEntity getPlayerMyself() {
    return this.isServer ? this.gameEntity.getServerPlayer() : this.gameEntity.getClientPlayer();
  }

  public PlayerEntity getPlayerOpponent() {
    return this.isServer ? this.gameEntity.getClientPlayer() : this.gameEntity.getServerPlayer();
  }
}
