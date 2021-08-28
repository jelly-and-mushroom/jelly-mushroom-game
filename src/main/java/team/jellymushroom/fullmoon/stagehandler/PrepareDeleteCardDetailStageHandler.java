package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.StageHandlerService;

/**
 * PREPARE_DELETE_CARD_DETAIL
 */
public class PrepareDeleteCardDetailStageHandler extends CardListDetailStageHandler {

  public PrepareDeleteCardDetailStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean cancel() {
    super.activePlayer.setStage(GameStageEnum.PREPARE_DELETE_CARD);
    return true;
  }
}
