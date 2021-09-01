package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.StageHandlerService;
import team.jellymushroom.fullmoon.ui.module.ChooseBlessingModule;

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
      super.activePlayer.getSignal().setBlessingList(this.stageHandlerService.getRecommendService().recommendBlessing(super.activePlayer, ChooseBlessingModule.BLESSING_COUNT));
      super.activePlayer.getSignal().setIndex(0);
      super.activePlayer.setStage(GameStageEnum.CHOOSE_BLESSING);
      super.passivePlayer.getSignal().setBlessingList(this.stageHandlerService.getRecommendService().recommendBlessing(super.passivePlayer, ChooseBlessingModule.BLESSING_COUNT));
      super.passivePlayer.getSignal().setIndex(0);
      super.passivePlayer.setStage(GameStageEnum.CHOOSE_BLESSING);
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
