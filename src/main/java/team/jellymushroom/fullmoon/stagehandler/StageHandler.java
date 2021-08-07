package team.jellymushroom.fullmoon.stagehandler;

import lombok.extern.slf4j.Slf4j;
import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.SignalEntity;
import team.jellymushroom.fullmoon.runnable.HttpUpdateGameRunnable;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

import java.util.Map;

@Slf4j
public abstract class StageHandler {

  MainService mainService;

  ResourceService resourceService;

  HttpTransferService httpTransferService;

  Boolean fromLocal;

  PlayerEntity activePlayer;

  PlayerEntity passivePlayer;

  StageHandler(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, Boolean fromLocal) {
    this.mainService = mainService;
    this.resourceService = resourceService;
    this.httpTransferService = httpTransferService;
    this.fromLocal = fromLocal;
    this.activePlayer = fromLocal ? this.mainService.getGameEntity().getServerPlayer() : this.mainService.getGameEntity().getClientPlayer();
    this.passivePlayer = fromLocal ? this.mainService.getGameEntity().getClientPlayer() : this.mainService.getGameEntity().getServerPlayer();
  }

  public void handle(KeyEventEnum keyEventEnum) {
    log.info("开始处理键盘指令,keyEventEnum:{},fromLocal:{},处理前所处状态:{}", keyEventEnum, this.fromLocal, this.activePlayer.getStage());
    switch (keyEventEnum) {
      case LEFT:
        this.left();
        break;
      case RIGHT:
        this.right();
        break;
      case UP:
        this.up();
        break;
      case DOWN:
        this.down();
        break;
      case DETAIL:
        this.detail();
        break;
      case CONFIRM:
        this.confirm();
        break;
      case CANCEL:
        this.cancel();
    }
  }

  void sendDataToClient() {
    new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
  }

  void updateSignalRole(int delta) {
    // 信号
    SignalEntity signal = this.activePlayer.getSignal();
    // 所有可选职业
    Map<Integer, GameRoleEntity> gameRoleMap = this.resourceService.getServiceResourceEntity().getGameRoleMap();
    // 变更后的角色值
    int preResult = signal.getIndex() + delta;
    // 小于下限则设为上限
    if (preResult < 0) {
      signal.setIndex(gameRoleMap.size() - 1);
      return;
    }
    // 大于上限则设为下限
    if (preResult >= gameRoleMap.size()) {
      signal.setIndex(gameRoleMap.get(0).getIndex());
      return;
    }
    // 设置为变更后的值
    signal.setIndex(preResult);
  }

  abstract void left();

  abstract void right();

  abstract void up();

  abstract void down();

  abstract void detail();

  abstract void confirm();

  abstract void cancel();
}
