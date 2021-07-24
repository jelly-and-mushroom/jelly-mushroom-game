package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.constant.PrepareEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.http.HttpServerControlEntity;
import team.jellymushroom.fullmoon.runnable.HttpSendKeyEventRunnable;
import team.jellymushroom.fullmoon.runnable.HttpUpdateGameRunnable;

@Service
@Slf4j
public class KeyEventService {

  private MainService mainService;

  private ChooseRoleService chooseRoleService;

  private HttpTransferService httpTransferService;

  public KeyEventService(MainService mainService, ChooseRoleService chooseRoleService, HttpTransferService httpTransferService) {
    this.mainService = mainService;
    this.chooseRoleService = chooseRoleService;
    this.httpTransferService = httpTransferService;
  }

  /**
   * @param keyCode 键盘事件code ( keyEvent.keyCode )
   * @param fromLocal true-自身按键 false:联机对手按键
   */
  public void keyPressed(int keyCode, boolean fromLocal) {
    if (ServerControlEntity.getInstance().getHttpSendWait()) {
      log.info("尚有未完成的http通信，不进行按键同步");
      return;
    }
    // 判断按键是否有效
    KeyEventEnum keyEventEnum = KeyEventEnum.getEnumByKeyCode(keyCode);
    if (null == keyEventEnum) {
      return;
    }
    // 尚未选出服务端，不接受按键
    Boolean isServer = ServerControlEntity.getInstance().getIsServer();
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
    }
  }

  private void handleChooseRole(boolean fromLocal, PlayerEntity activePlayer, PlayerEntity passivePlayer, KeyEventEnum keyEventEnum) {
    switch (keyEventEnum) {
      case LEFT:
        if (fromLocal) {
          this.chooseRoleService.updateRole(-1);
          HttpServerControlEntity serverControl = new HttpServerControlEntity();
          serverControl.setOpponentCurrentChooseRoleIndex(ServerControlEntity.getInstance().getCurrentChooseRole().getIndex());
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, null)).start();
        } else {
          this.chooseRoleService.updateOpponentRole(-1);
          HttpServerControlEntity serverControl = new HttpServerControlEntity();
          serverControl.setCurrentChooseRoleIndex(ServerControlEntity.getInstance().getOpponentCurrentChooseRole().getIndex());
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, null)).start();
        }
        break;
      case RIGHT:
        if (fromLocal) {
          this.chooseRoleService.updateRole(1);
          HttpServerControlEntity serverControl = new HttpServerControlEntity();
          serverControl.setOpponentCurrentChooseRoleIndex(ServerControlEntity.getInstance().getCurrentChooseRole().getIndex());
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, null)).start();
        } else {
          this.chooseRoleService.updateOpponentRole(1);
          HttpServerControlEntity serverControl = new HttpServerControlEntity();
          serverControl.setCurrentChooseRoleIndex(ServerControlEntity.getInstance().getOpponentCurrentChooseRole().getIndex());
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, null)).start();
        }
        break;
      case DETAIL:
        activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_DETAIL);
        new Thread(new HttpUpdateGameRunnable(this.httpTransferService, null, this.mainService.getGameEntity())).start();
        break;
      case CONFIRM:
        HttpServerControlEntity serverControl = new HttpServerControlEntity();
        if (fromLocal) {
          this.chooseRoleService.confirm();
        } else {
          this.chooseRoleService.confirmOpponent();
        }
        if (GameStageEnum.CHOOSE_ROLE_CONFIRM.equals(passivePlayer.getStage())) {
          activePlayer.setStage(GameStageEnum.PREPARE);
          passivePlayer.setStage(GameStageEnum.PREPARE);
          ServerControlEntity.getInstance().setCurrentPrepare(PrepareEnum.MY_CARD_REPOSITORY);
          ServerControlEntity.getInstance().setOpponentPrepare(PrepareEnum.MY_CARD_REPOSITORY);
          serverControl.setCurrentPrepareIndex(ServerControlEntity.getInstance().getOpponentPrepare().getIndex());
        } else {
          activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_CONFIRM);
        }
        new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, this.mainService.getGameEntity())).start();
    }
  }

  private void handleRoleChooseDetail(boolean fromLocal, PlayerEntity activePlayer, KeyEventEnum keyEventEnum) {
    switch (keyEventEnum) {
      case LEFT:
        if (fromLocal) {
          this.chooseRoleService.updateRole(-1);
          HttpServerControlEntity serverControl = new HttpServerControlEntity();
          serverControl.setOpponentCurrentChooseRoleIndex(ServerControlEntity.getInstance().getCurrentChooseRole().getIndex());
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, null)).start();
        } else {
          this.chooseRoleService.updateOpponentRole(-1);
          HttpServerControlEntity serverControl = new HttpServerControlEntity();
          serverControl.setCurrentChooseRoleIndex(ServerControlEntity.getInstance().getOpponentCurrentChooseRole().getIndex());
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, null)).start();
        }
        break;
      case RIGHT:
        if (fromLocal) {
          this.chooseRoleService.updateRole(1);
          HttpServerControlEntity serverControl = new HttpServerControlEntity();
          serverControl.setOpponentCurrentChooseRoleIndex(ServerControlEntity.getInstance().getCurrentChooseRole().getIndex());
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, null)).start();
        } else {
          this.chooseRoleService.updateOpponentRole(1);
          HttpServerControlEntity serverControl = new HttpServerControlEntity();
          serverControl.setCurrentChooseRoleIndex(ServerControlEntity.getInstance().getOpponentCurrentChooseRole().getIndex());
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, serverControl, null)).start();
        }
        break;
      case CANCEL:
        activePlayer.setStage(GameStageEnum.CHOOSE_ROLE);
        if (!fromLocal) {
          new Thread(new HttpUpdateGameRunnable(this.httpTransferService, null, this.mainService.getGameEntity())).start();
        }
    }
  }
}
