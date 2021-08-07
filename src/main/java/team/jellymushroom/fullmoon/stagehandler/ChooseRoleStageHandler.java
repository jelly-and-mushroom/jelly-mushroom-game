package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

/**
 * GameStageEnum.CHOOSE_ROLE
 */
public class ChooseRoleStageHandler extends BaseChooseRoleStageHandler {

  public ChooseRoleStageHandler(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, Boolean fromLocal) {
    super(mainService, resourceService, httpTransferService, fromLocal);
  }

  @Override
  boolean detail() {
    super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_DETAIL);
    return true;
  }

  @Override
  boolean confirm() {
    super.activePlayer.initByRole(this.resourceService);
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
