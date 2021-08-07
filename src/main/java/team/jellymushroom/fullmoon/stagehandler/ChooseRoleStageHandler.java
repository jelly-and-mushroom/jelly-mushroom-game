package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.StageHandlerService;

/**
 * GameStageEnum.CHOOSE_ROLE
 */
public class ChooseRoleStageHandler extends BaseChooseRoleStageHandler {

  public ChooseRoleStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean detail() {
    super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_DETAIL);
    return true;
  }

  @Override
  boolean confirm() {
    super.activePlayer.initByRole(this.stageHandlerService.getResourceService());
    if (GameStageEnum.CHOOSE_ROLE_CONFIRM.equals(super.passivePlayer.getStage())) {
      super.activePlayer.setStage(GameStageEnum.PREPARE);
      super.passivePlayer.setStage(GameStageEnum.PREPARE);
    } else {
      super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_CONFIRM);
    }
    return true;
  }

  @Override
  boolean cancel() {
    return false;
  }
}
