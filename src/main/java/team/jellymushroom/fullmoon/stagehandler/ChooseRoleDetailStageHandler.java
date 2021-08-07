package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

/**
 * GameStageEnum.CHOOSE_ROLE_DETAIL
 */
public class ChooseRoleDetailStageHandler extends BaseChooseRoleStageHandler {

  public ChooseRoleDetailStageHandler(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, Boolean fromLocal) {
    super(mainService, resourceService, httpTransferService, fromLocal);
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
