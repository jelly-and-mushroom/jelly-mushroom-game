package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.http.HttpServerControlEntity;
import team.jellymushroom.fullmoon.runnable.HttpUpdateGameRunnable;
import team.jellymushroom.fullmoon.runnable.HttpWaitConnectRunnable;

import javax.annotation.PostConstruct;

@Service
public class MainService {

  @Getter
  @Setter
  private GameEntity gameEntity;

  @Value("${fm.http.opponent.host}")
  @Getter
  private String httpOpponentHost;

  private ResourceService resourceService;

  public static final Long HTTP_RETRY_INTERVAL = 1000L;

  public MainService(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  @PostConstruct
  public void init() {
    new Thread(new HttpWaitConnectRunnable(this)).start();
  }

  public PlayerEntity getPlayerMyself() {
    return ServerControlEntity.getInstance().getIsServer() ? this.gameEntity.getServerPlayer() : this.gameEntity.getClientPlayer();
  }

  public PlayerEntity getPlayerOpponent() {
    return ServerControlEntity.getInstance().getIsServer() ? this.gameEntity.getClientPlayer() : this.gameEntity.getServerPlayer();
  }

  public void initGame() {
    // 初始化游戏
    this.gameEntity = new GameEntity();
    ServerControlEntity.getInstance().setCurrentChooseRole(this.resourceService.getServiceResourceEntity().getGameRoleMap().get(0));
    ServerControlEntity.getInstance().setOpponentCurrentChooseRole(this.resourceService.getServiceResourceEntity().getGameRoleMap().get(0));
    ServerControlEntity.getInstance().setIsServer(true);
    // 同步数据给客户端
    HttpServerControlEntity serverControl = new HttpServerControlEntity();
    serverControl.setCurrentChooseRole(ServerControlEntity.getInstance().getOpponentCurrentChooseRole());
    serverControl.setOpponentCurrentChooseRole(ServerControlEntity.getInstance().getCurrentChooseRole());
    new Thread(new HttpUpdateGameRunnable(this, serverControl, this.getGameEntity())).start();
  }
}
