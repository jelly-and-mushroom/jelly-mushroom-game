package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.runnable.HttpSendKeyEventRunnable;
import team.jellymushroom.fullmoon.runnable.HttpUpdateGameRunnable;
import team.jellymushroom.fullmoon.stagehandler.ChooseRoleStageHandler;
import team.jellymushroom.fullmoon.stagehandler.PrepareStageHandler;

@Service
@Slf4j
public class KeyEventService {

  private MainService mainService;

  private ResourceService resourceService;

  private HttpTransferService httpTransferService;

  private PrepareService prepareService;

  private PrepareCardListService prepareCardListService;

  private CardRecommendService cardRecommendService;

  public KeyEventService(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, PrepareService prepareService, PrepareCardListService prepareCardListService, CardRecommendService cardRecommendService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
    this.httpTransferService = httpTransferService;
    this.prepareService = prepareService;
    this.prepareCardListService = prepareCardListService;
    this.cardRecommendService = cardRecommendService;
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
      case CHOOSE_ROLE_DETAIL:
        new ChooseRoleStageHandler(this.mainService, this.resourceService, this.httpTransferService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE:
        new PrepareStageHandler(this.mainService, this.resourceService, this.httpTransferService, fromLocal).handle(keyEventEnum);
        break;
      case PREPARE_MY_CARD_REPOSITORY:
        this.handlePrepareMyCardRepository(fromLocal, keyEventEnum);
        break;
      case PREPARE_MY_CARD_REPOSITORY_DETAIL:
        this.handlePrepareMyCardRepositoryDetail(fromLocal, keyEventEnum);
        break;
      case PREPARE_BY_CARD:
        this.handlePrepareByCard(fromLocal, keyEventEnum);
    }
  }

  private void handlePrepareMyCardRepository(boolean fromLocal, KeyEventEnum keyEventEnum) {
    PlayerEntity player = fromLocal ? this.mainService.getPlayerMyself() : this.mainService.getPlayerOpponent();
    int cardListSize = player.getCardList().size();
    if (cardListSize == 0 && !KeyEventEnum.CANCEL.equals(keyEventEnum)) {
      return;
    }
    switch (keyEventEnum) {
      case LEFT:
        this.prepareCardListService.moveLeft(cardListSize, fromLocal);
        if (!fromLocal) {
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case RIGHT:
        this.prepareCardListService.moveRight(cardListSize, fromLocal);
        if (!fromLocal) {
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case UP:
        this.prepareCardListService.moveUp(cardListSize, fromLocal);
        if (!fromLocal) {
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case DOWN:
        this.prepareCardListService.moveDown(cardListSize, fromLocal);
        if (!fromLocal) {
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case DETAIL:
        player.setStage(GameStageEnum.PREPARE_MY_CARD_REPOSITORY_DETAIL);
        if (!fromLocal) {
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case CONFIRM:
        boolean confirmNeedSend = this.prepareCardListService.confirm(fromLocal);
        if (!fromLocal && confirmNeedSend) {
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case CANCEL:
        player.setStage(GameStageEnum.PREPARE);
        player.getSignal().setIndex(0);
        new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
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
