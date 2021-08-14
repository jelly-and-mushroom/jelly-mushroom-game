package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.StageHandlerService;

/**
 * PREPARE_BUY_CARD_RANDOM
 */
public class PrepareBuyCardRandomStageHandler extends StageHandler {

  public PrepareBuyCardRandomStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean left() {
    return false;
  }

  @Override
  boolean right() {
    return false;
  }

  @Override
  boolean up() {
    return false;
  }

  @Override
  boolean down() {
    return false;
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
    super.activePlayer.setStage(GameStageEnum.PREPARE_BUY_CARD);
    return true;
  }
}
