package team.jellymushroom.fullmoon.stagehandler;

import lombok.extern.slf4j.Slf4j;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.runnable.HttpUpdateGameRunnable;
import team.jellymushroom.fullmoon.service.StageHandlerService;

@Slf4j
public abstract class StageHandler {

  StageHandlerService stageHandlerService;

  Boolean fromLocal;

  PlayerEntity activePlayer;

  PlayerEntity passivePlayer;

  StageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    this.stageHandlerService = stageHandlerService;
    this.fromLocal = fromLocal;
    this.activePlayer = fromLocal ? this.stageHandlerService.getMainService().getGameEntity().getServerPlayer() : this.stageHandlerService.getMainService().getGameEntity().getClientPlayer();
    this.passivePlayer = fromLocal ? this.stageHandlerService.getMainService().getGameEntity().getClientPlayer() : this.stageHandlerService.getMainService().getGameEntity().getServerPlayer();
  }

  public void handle(KeyEventEnum keyEventEnum) {
    GameStageEnum stage = this.activePlayer.getStage();
    boolean needSend = false;
    switch (keyEventEnum) {
      case LEFT:
        needSend = this.left();
        break;
      case RIGHT:
        needSend = this.right();
        break;
      case UP:
        needSend = this.up();
        break;
      case DOWN:
        needSend = this.down();
        break;
      case DETAIL:
        needSend = this.detail();
        break;
      case CONFIRM:
        needSend = this.confirm();
        break;
      case CANCEL:
        needSend = this.cancel();
    }
    if (needSend) {
      new Thread(new HttpUpdateGameRunnable(this.stageHandlerService.getHttpTransferService(), this.stageHandlerService.getMainService().getGameEntity())).start();
    }
    log.info("键盘指令处理完成,keyEventEnum:{},fromLocal:{},needSend:{},处理前所处状态:{},处理后所处状态:{}", keyEventEnum, this.fromLocal, needSend, stage, this.activePlayer.getStage());
  }

  abstract boolean left();

  abstract boolean right();

  abstract boolean up();

  abstract boolean down();

  abstract boolean detail();

  abstract boolean confirm();

  abstract boolean cancel();
}