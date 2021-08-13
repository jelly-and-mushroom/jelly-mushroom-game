package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.service.StageHandlerService;

public class PrepareMyCardRepositoryDetailStageHandler extends CardListDetailStageHandler {

  public PrepareMyCardRepositoryDetailStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean cancel() {
    super.activePlayer.setStage(GameStageEnum.PREPARE_MY_CARD_REPOSITORY);
    return true;
  }
}
