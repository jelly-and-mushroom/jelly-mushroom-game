package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

/**
 * GameStageEnum.CHOOSE_ROLE
 */
public class ChooseRoleStageHandler extends StageHandler {

  public ChooseRoleStageHandler(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, Boolean fromLocal) {
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
    super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_DETAIL);
    super.sendDataToClient();
  }

  @Override
  void confirm() {
    super.activePlayer.initByRole(this.resourceService);
    if (GameStageEnum.CHOOSE_ROLE_CONFIRM.equals(super.passivePlayer.getStage())) {
      super.activePlayer.getSignal().setIndex(0);
      super.passivePlayer.getSignal().setIndex(0);
      super.activePlayer.setStage(GameStageEnum.PREPARE);
      super.passivePlayer.setStage(GameStageEnum.PREPARE);
    } else {
      super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_CONFIRM);
    }
    super.sendDataToClient();
  }

  @Override
  void cancel() {
  }
}
