package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.StageHandlerService;

/**
 * PREPARE_INTENSIFY_CARD_DETAIL
 */
public class PrepareIntensifyCardDetailStageHandler extends CardListDetailStageHandler {

  public PrepareIntensifyCardDetailStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean cancel() {
    super.activePlayer.setStage(GameStageEnum.PREPARE_INTENSIFY_CARD);
    return true;
  }
}
