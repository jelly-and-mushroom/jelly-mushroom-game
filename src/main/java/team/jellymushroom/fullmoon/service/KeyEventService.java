package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Service
@Slf4j
public class KeyEventService {

  private MainService mainService;

  private ChooseRoleService chooseRoleService;

  public KeyEventService(MainService mainService, ChooseRoleService chooseRoleService) {
    this.mainService = mainService;
    this.chooseRoleService = chooseRoleService;
  }

  /**
   * @param keyCode 键盘事件code ( keyEvent.keyCode )
   * @param fromLocal true-自身按键 false:联机对手按键
   */
  public void keyPressed(int keyCode, boolean fromLocal) {
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
      // TODO
      return;
    }
    // 服务端处理事件
    PlayerEntity activePlayer = fromLocal ? this.mainService.getGameEntity().getServerPlayer() : this.mainService.getGameEntity().getClientPlayer();
    log.info("服务端开始处理键盘指令,keyCode:{},fromLocal:{},发送指令玩家所处状态:{}", keyCode, fromLocal, activePlayer.getStage());
    switch (activePlayer.getStage()) {
      case CHOOSE_ROLE:
        this.handleChooseRole(activePlayer, keyEventEnum);
        break;
      case CHOOSE_ROLE_DETAIL:
        this.handleRoleChooseDetail(activePlayer, keyEventEnum);
    }
  }

  private void handleChooseRole(PlayerEntity activePlayer, KeyEventEnum keyEventEnum) {
    switch (keyEventEnum) {
      case LEFT:
        this.chooseRoleService.updateRole(-1);
        break;
      case RIGHT:
        this.chooseRoleService.updateRole(1);
        break;
      case DETAIL:
        activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_DETAIL);
        break;
      case CONFIRM:
        this.chooseRoleService.confirm();
        activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_CONFIRM);
    }
  }

  private void handleRoleChooseDetail(PlayerEntity activePlayer, KeyEventEnum keyEventEnum) {
    switch (keyEventEnum) {
      case LEFT:
        this.chooseRoleService.updateRole(-1);
        break;
      case RIGHT:
        this.chooseRoleService.updateRole(1);
        break;
      case CANCEL:
        activePlayer.setStage(GameStageEnum.CHOOSE_ROLE);
    }
  }
}
