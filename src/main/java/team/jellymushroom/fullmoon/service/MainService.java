package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.runnable.HttpUpdateGameRunnable;
import team.jellymushroom.fullmoon.runnable.HttpWaitConnectRunnable;

import javax.annotation.PostConstruct;
import java.util.Map;

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

  private ResourceService resourceService;

  private HttpTransferService httpTransferService;

  public MainService(ResourceService resourceService, HttpTransferService httpTransferService) {
    this.resourceService = resourceService;
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

  public void initGame() {
    // 初始化
    this.isServer = true;
    Map<Integer, GameRoleEntity> gameRoleMap = this.resourceService.getServiceResourceEntity().getGameRoleMap();
    this.getPlayerMyself().getSignal().setIndex(gameRoleMap.get(0).getIndex());
    this.getPlayerOpponent().getSignal().setIndex(gameRoleMap.get(0).getIndex());
    // 同步数据给客户端
    new Thread(new HttpUpdateGameRunnable(this.httpTransferService, null, this.getGameEntity())).start();
  }
}
