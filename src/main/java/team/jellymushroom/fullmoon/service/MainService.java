package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import lombok.Setter;
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
    serverControl.setCurrentChooseRoleIndex(ServerControlEntity.getInstance().getOpponentCurrentChooseRole().getIndex());
    serverControl.setOpponentCurrentChooseRoleIndex(ServerControlEntity.getInstance().getCurrentChooseRole().getIndex());
    new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, this.getGameEntity())).start();
  }
}
