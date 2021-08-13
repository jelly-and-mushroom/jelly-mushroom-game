package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.service.StageHandlerService;

public abstract class CardListDetailStageHandler extends StageHandler {

  CardListDetailStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
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
}
