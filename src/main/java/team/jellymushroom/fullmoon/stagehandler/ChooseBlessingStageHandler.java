package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.BlessingEntity;
import team.jellymushroom.fullmoon.service.StageHandlerService;

import java.util.List;

/**
 * GameStageEnum.CHOOSE_BLESSING
 */
public class ChooseBlessingStageHandler extends StageHandler {

  public ChooseBlessingStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
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
    List<BlessingEntity> blessingList = super.activePlayer.getSignal().getBlessingList();
    if (blessingList.isEmpty() || blessingList.size() == 1) {
      return false;
    }
    int wishIndex = super.activePlayer.getSignal().getIndex() - 1;
    if (wishIndex < 0) {
      wishIndex = blessingList.size() - 1;
    }
    super.activePlayer.getSignal().setIndex(wishIndex);
    return true;
  }

  @Override
  boolean down() {
    List<BlessingEntity> blessingList = super.activePlayer.getSignal().getBlessingList();
    if (blessingList.isEmpty() || blessingList.size() == 1) {
      return false;
    }
    int wishIndex = super.activePlayer.getSignal().getIndex() + 1;
    if (wishIndex >= blessingList.size()) {
      wishIndex = 0;
    }
    super.activePlayer.getSignal().setIndex(wishIndex);
    return true;
  }

  @Override
  boolean detail() {
    return false;
  }

  @Override
  boolean confirm() {
    List<BlessingEntity> blessingList = super.activePlayer.getSignal().getBlessingList();
    if (!blessingList.isEmpty()) {
      super.activePlayer.getBlessingList().add(super.activePlayer.getSignal().getBlessingList().get(super.activePlayer.getSignal().getIndex()));
    }
    super.activePlayer.getSignal().setIndex(0);
    super.activePlayer.setStage(GameStageEnum.PREPARE);
    return true;
  }

  @Override
  boolean cancel() {
    return false;
  }
}
