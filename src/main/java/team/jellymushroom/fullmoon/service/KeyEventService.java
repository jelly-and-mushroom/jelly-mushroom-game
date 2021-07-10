package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.entity.game.GameEntity;

import java.awt.event.KeyEvent;

@Service
@Slf4j
public class KeyEventService {

  private MainService mainService;

  private RoleChooseService roleChooseService;

  public KeyEventService(MainService mainService, RoleChooseService roleChooseService) {
    this.mainService = mainService;
    this.roleChooseService = roleChooseService;
  }

  public void keyPressed(KeyEvent keyEvent) {
    GameEntity game = this.mainService.getGameEntity();
    KeyEventEnum keyEventEnum = KeyEventEnum.getEnumByValue(keyEvent.getKeyCode());
    String keyEventDesc = null==keyEventEnum ? null : keyEventEnum.getDescription();
    switch (game.getStage()) {
      case CHOOSE_ROLE:
        log.info("所处游戏阶段:{},监听到键盘输入:{},对应键盘事件:{}", game.getStage().getName(), keyEvent.getKeyCode(), keyEventDesc);
        this.handleRoleChoose(keyEventEnum);
        break;
    }
  }

  private void handleRoleChoose(KeyEventEnum keyEventEnum) {
    if (null == keyEventEnum) {
      return;
    }
    switch (keyEventEnum) {
      case LEFT:
        this.roleChooseService.updateRole(-1);
        break;
      case RIGHT:
        this.roleChooseService.updateRole(1);
        break;
      case DETAIL:
        this.roleChooseService.setShowDetail(true);
        break;
      case CANCEL:
        this.roleChooseService.setShowDetail(false);
        break;
      case CONFIRM:
        this.roleChooseService.confirm();
    }
  }
}
