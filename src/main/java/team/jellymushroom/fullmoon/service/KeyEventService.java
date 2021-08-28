package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.runnable.HttpSendKeyEventRunnable;
import team.jellymushroom.fullmoon.stagehandler.*;

@Service
@Slf4j
public class KeyEventService {

  private StageHandlerService stageHandlerService;

  public KeyEventService(StageHandlerService stageHandlerService) {
    this.stageHandlerService = stageHandlerService;
  }

  /**
   * @param keyCode 键盘事件code ( keyEvent.keyCode )
   * @param fromLocal true-自身按键 false:联机对手按键
   */
  public void keyPressed(int keyCode, boolean fromLocal) {
    if (this.stageHandlerService.getHttpTransferService().getHttpSendWait()) {
      log.info("尚有未完成的http通信，不进行按键同步");
      return;
    }
    // 判断按键是否有效
    KeyEventEnum keyEventEnum = KeyEventEnum.getEnumByKeyCode(keyCode);
    if (null == keyEventEnum) {
      return;
    }
    // 尚未选出服务端，不接受按键
    Boolean isServer = this.stageHandlerService.getMainService().getIsServer();
    if (null == isServer) {
      return;
    }
    // 客户端处理事件
    if (!isServer) {
      new Thread(new HttpSendKeyEventRunnable(this.stageHandlerService.getHttpTransferService(), keyCode)).start();
      return;
    }
    // 服务端处理事件
    PlayerEntity activePlayer = fromLocal ? this.stageHandlerService.getMainService().getGameEntity().getServerPlayer() : this.stageHandlerService.getMainService().getGameEntity().getClientPlayer();
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
        new PrepareMyCardRepositoryDetailStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_BUY_CARD:
        new PrepareBuyCardStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_BUY_CARD_DETAIL:
        new PrepareBuyCardDetailStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_BUY_CARD_RANDOM:
        new PrepareBuyCardRandomStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_INTENSIFY_CARD:
        new PrepareIntensifyCardStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_INTENSIFY_CARD_DETAIL:
        new PrepareIntensifyCardDetailStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_DELETE_CARD:
        new PrepareDeleteCardStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_DELETE_CARD_DETAIL:
        new PrepareDeleteCardDetailStageHandler(this.stageHandlerService, fromLocal).handle(keyEventEnum);
        break;
    }
  }
}
