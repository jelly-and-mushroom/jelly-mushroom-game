package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

/**
 * GameStageEnum.CHOOSE_ROLE_DETAIL
 */
public class ChooseRoleDetailStageHandler extends StageHandler {

  public ChooseRoleDetailStageHandler(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, Boolean fromLocal) {
    super(mainService, resourceService, httpTransferService, fromLocal);
  }

  @Override
  void left() {
    super.updateSignalRole(-1);
    super.sendDataToClient();
  }

  @Override
  void right() {
    super.updateSignalRole(1);
    super.sendDataToClient();
  }

  @Override
  void up() {
  }

  @Override
  void down() {
  }

  @Override
  void detail() {
  }

  @Override
  void confirm() {
  }

  @Override
  void cancel() {
    super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE);
    if (!fromLocal) {
      super.sendDataToClient();
    }
  }
}
