package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.StageHandlerService;

public class PrepareBuyCardDetailStageHandler extends CardListDetailStageHandler {

  public PrepareBuyCardDetailStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean cancel() {
    super.activePlayer.setStage(GameStageEnum.PREPARE_BUY_CARD);
    return true;
  }
}
