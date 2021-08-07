package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.StageHandlerService;

/**
 * GameStageEnum.CHOOSE_ROLE_DETAIL
 */
public class ChooseRoleDetailStageHandler extends BaseChooseRoleStageHandler {

  public ChooseRoleDetailStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean detail() {
    return false;
  }

  @Override
  boolean confirm() {
    return false;
  }

  @Override
  boolean cancel() {
    super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE);
    return true;
  }
}
