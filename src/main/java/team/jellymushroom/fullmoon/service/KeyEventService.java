package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.SignalEntity;
import team.jellymushroom.fullmoon.runnable.HttpSendKeyEventRunnable;
import team.jellymushroom.fullmoon.runnable.HttpUpdateGameRunnable;

@Service
@Slf4j
public class KeyEventService {

  private MainService mainService;

  private ChooseRoleService chooseRoleService;

  private HttpTransferService httpTransferService;

  private PrepareService prepareService;

  private PrepareCardListService prepareCardListService;

  private CardRecommendService cardRecommendService;

  public KeyEventService(MainService mainService, ChooseRoleService chooseRoleService, HttpTransferService httpTransferService, PrepareService prepareService, PrepareCardListService prepareCardListService, CardRecommendService cardRecommendService) {
    this.mainService = mainService;
    this.chooseRoleService = chooseRoleService;
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
    PlayerEntity passivePlayer = fromLocal ? this.mainService.getGameEntity().getClientPlayer() : this.mainService.getGameEntity().getServerPlayer();
    log.info("服务端开始处理键盘指令,keyCode:{},fromLocal:{},发送指令玩家所处状态:{}", keyCode, fromLocal, activePlayer.getStage());
    switch (activePlayer.getStage()) {
      case CHOOSE_ROLE:
        this.handleChooseRole(fromLocal, activePlayer, passivePlayer, keyEventEnum);
        break;
      case CHOOSE_ROLE_DETAIL:
        this.handleRoleChooseDetail(fromLocal, activePlayer, keyEventEnum);
        break;
      case PREPARE:
        this.handlePrepare(fromLocal, keyEventEnum);
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

  private void handleChooseRole(boolean fromLocal, PlayerEntity activePlayer, PlayerEntity passivePlayer, KeyEventEnum keyEventEnum) {
    switch (keyEventEnum) {
      case LEFT:
        if (fromLocal) {
          this.chooseRoleService.updateRole(-1);
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        } else {
          this.chooseRoleService.updateOpponentRole(-1);
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case RIGHT:
        if (fromLocal) {
          this.chooseRoleService.updateRole(1);
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        } else {
          this.chooseRoleService.updateOpponentRole(1);
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case DETAIL:
        activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_DETAIL);
        new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        break;
      case CONFIRM:
        if (fromLocal) {
          this.chooseRoleService.confirm();
        } else {
          this.chooseRoleService.confirmOpponent();
        }
        if (GameStageEnum.CHOOSE_ROLE_CONFIRM.equals(passivePlayer.getStage())) {
          activePlayer.getSignal().setIndex(0);
          passivePlayer.getSignal().setIndex(0);
          activePlayer.getSignal().setPrepareOption(PrepareOptionEnum.MY_CARD_REPOSITORY);
          passivePlayer.getSignal().setPrepareOption(PrepareOptionEnum.MY_CARD_REPOSITORY);
          activePlayer.setStage(GameStageEnum.PREPARE);
          passivePlayer.setStage(GameStageEnum.PREPARE);
        } else {
          activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_CONFIRM);
        }
        new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
    }
  }

  private void handleRoleChooseDetail(boolean fromLocal, PlayerEntity activePlayer, KeyEventEnum keyEventEnum) {
    switch (keyEventEnum) {
      case LEFT:
        if (fromLocal) {
          this.chooseRoleService.updateRole(-1);
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        } else {
          this.chooseRoleService.updateOpponentRole(-1);
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case RIGHT:
        if (fromLocal) {
          this.chooseRoleService.updateRole(1);
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        } else {
          this.chooseRoleService.updateOpponentRole(1);
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
        break;
      case CANCEL:
        activePlayer.setStage(GameStageEnum.CHOOSE_ROLE);
        if (!fromLocal) {
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
    }
  }

  private void handlePrepare(boolean fromLocal, KeyEventEnum keyEventEnum) {
    SignalEntity signal = fromLocal ? this.mainService.getGameEntity().getServerPlayer().getSignal() : this.mainService.getGameEntity().getClientPlayer().getSignal();
    PrepareOptionEnum prepare = signal.getPrepareOption();
    PrepareOptionEnum nextPrepare = null;
    switch (keyEventEnum) {
      case LEFT:
        signal.setPrepareOption(PrepareOptionEnum.getEnumByIndex(prepare.getLeftIndex()));
        new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        break;
      case RIGHT:
        signal.setPrepareOption(PrepareOptionEnum.getEnumByIndex(prepare.getRightIndex()));
        new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        break;
      case UP:
        signal.setPrepareOption(PrepareOptionEnum.getEnumByIndex(prepare.getUpIndex()));
        new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        break;
      case DOWN:
        signal.setPrepareOption(PrepareOptionEnum.getEnumByIndex(prepare.getDownIndex()));
        new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        break;
      case CONFIRM:
        if (fromLocal) {
          this.prepareService.confirm();
          if (PrepareOptionEnum.MY_CARD_REPOSITORY.equals(prepare)) {
            this.mainService.getPlayerMyself().setStage(GameStageEnum.PREPARE_MY_CARD_REPOSITORY);
          } else if (PrepareOptionEnum.BY_CARD.equals(prepare)) {
            this.mainService.getPlayerMyself().setStage(GameStageEnum.PREPARE_BY_CARD);
          }
        } else {
          this.prepareService.confirmOpponent();
          if (PrepareOptionEnum.MY_CARD_REPOSITORY.equals(prepare)) {
            this.mainService.getPlayerOpponent().setStage(GameStageEnum.PREPARE_MY_CARD_REPOSITORY);
          } else if (PrepareOptionEnum.BY_CARD.equals(prepare)) {
            this.mainService.getPlayerOpponent().setStage(GameStageEnum.PREPARE_BY_CARD);
          }
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
        }
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
