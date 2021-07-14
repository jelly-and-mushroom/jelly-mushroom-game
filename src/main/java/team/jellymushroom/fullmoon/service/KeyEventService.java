package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
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
    log.info("所处游戏阶段:{},监听到键盘输入:{},对应键盘事件:{}", game.getStage().getName(), keyEvent.getKeyCode(), keyEventDesc);
    GameStageEnum newGameStage = null;
    switch (game.getStage()) {
      case CHOOSE_ROLE:
        newGameStage = this.handleRoleChoose(keyEventEnum);
        break;
      case CHOOSE_ROLE_DETAIL:
        newGameStage = this.handleRoleChooseDetail(keyEventEnum);
    }
    if (null != newGameStage) {
      game.setStage(newGameStage);
    }
  }

  private GameStageEnum handleRoleChoose(KeyEventEnum keyEventEnum) {
    if (null == keyEventEnum) {
      return null;
    }
    GameStageEnum result = null;
    switch (keyEventEnum) {
      case LEFT:
        this.roleChooseService.updateRole(-1);
        break;
      case RIGHT:
        this.roleChooseService.updateRole(1);
        break;
      case DETAIL:
        this.roleChooseService.setShowDetail(true);
        result =  GameStageEnum.CHOOSE_ROLE_DETAIL;
        break;
      case CONFIRM:
        this.roleChooseService.confirm();
        System.out.println("aa");
        result =  GameStageEnum.CHOOSE_ROLE_CONFIRM;
    }
    return result;
  }

  private GameStageEnum handleRoleChooseDetail(KeyEventEnum keyEventEnum) {
    if (null == keyEventEnum) {
      return null;
    }
    GameStageEnum result = null;
    switch (keyEventEnum) {
      case LEFT:
        this.roleChooseService.updateRole(-1);
        break;
      case RIGHT:
        this.roleChooseService.updateRole(1);
        break;
      case CANCEL:
        this.roleChooseService.setShowDetail(false);
        result = GameStageEnum.CHOOSE_ROLE;
    }
    return result;
  }
}
