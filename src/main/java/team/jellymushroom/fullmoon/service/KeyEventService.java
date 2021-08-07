package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.runnable.HttpSendKeyEventRunnable;
import team.jellymushroom.fullmoon.runnable.HttpUpdateGameRunnable;
import team.jellymushroom.fullmoon.stagehandler.ChooseRoleDetailStageHandler;
import team.jellymushroom.fullmoon.stagehandler.ChooseRoleStageHandler;
import team.jellymushroom.fullmoon.stagehandler.PrepareMyCardRepositoryStageHandler;
import team.jellymushroom.fullmoon.stagehandler.PrepareStageHandler;

@Service
@Slf4j
public class KeyEventService {

  private MainService mainService;

  private HttpTransferService httpTransferService;

  private StageHandlerService stageHandlerService;

  public KeyEventService(MainService mainService, HttpTransferService httpTransferService, StageHandlerService stageHandlerService) {
    this.mainService = mainService;
    this.httpTransferService = httpTransferService;
    this.stageHandlerService = stageHandlerService;
  }

  /**
   * @param keyCode 键盘事件code ( keyEvent.keyCode )
   * @param fromLocal true-自身按键 false:联机对手按键
   */
  public void keyPressed(int keyCode, boolean fromLocal) {
    if (this.httpTransferService.getHttpSendWait()) {
      log.info("尚有未完成的http通信，不进行按键同步");
      return;
    }
    // 判断按键是否有效
    KeyEventEnum keyEventEnum = KeyEventEnum.getEnumByKeyCode(keyCode);
    if (null == keyEventEnum) {
      return;
    }
    // 尚未选出服务端，不接受按键
    Boolean isServer = this.mainService.getIsServer();
    if (null == isServer) {
      return;
    }
    // 客户端处理事件
    if (!isServer) {
      new Thread(new HttpSendKeyEventRunnable(this.httpTransferService, keyCode)).start();
      return;
    }
    // 服务端处理事件
    PlayerEntity activePlayer = fromLocal ? this.mainService.getGameEntity().getServerPlayer() : this.mainService.getGameEntity().getClientPlayer();
    switch (activePlayer.getStage()) {
      case CHOOSE_ROLE:
        new ChooseRoleStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case CHOOSE_ROLE_DETAIL:
        new ChooseRoleDetailStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE:
        new PrepareStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_MY_CARD_REPOSITORY:
        new PrepareMyCardRepositoryStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_MY_CARD_REPOSITORY_DETAIL:
        this.handlePrepareMyCardRepositoryDetail(fromLocal, keyEventEnum);
        break;
      case PREPARE_BUY_CARD:
        this.handlePrepareByCard(fromLocal, keyEventEnum);
    }
  }

  private void handlePrepareMyCardRepositoryDetail(boolean fromLocal, KeyEventEnum keyEventEnum) {
    PlayerEntity player = fromLocal ? this.mainService.getPlayerMyself() : this.mainService.getPlayerOpponent();
    switch (keyEventEnum) {
      case CANCEL:
        player.setStage(GameStageEnum.PREPARE_MY_CARD_REPOSITORY);
        if (!fromLocal) {
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
    }
  }

  private void handlePrepareByCard(boolean fromLocal, KeyEventEnum keyEventEnum) {
  }
}
