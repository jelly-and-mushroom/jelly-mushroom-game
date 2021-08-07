package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.SignalEntity;
import team.jellymushroom.fullmoon.service.StageHandlerService;

/**
 * GameStageEnum.PREPARE
 */
public class PrepareStageHandler extends StageHandler {

  public PrepareStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean left() {
    SignalEntity signal = super.activePlayer.getSignal();
    PrepareOptionEnum nextPrepare = PrepareOptionEnum.getEnumByIndex(signal.getPrepareOption().getLeftIndex());
    signal.setPrepareOption(nextPrepare);
    return true;
  }

  @Override
  boolean right() {
    SignalEntity signal = super.activePlayer.getSignal();
    PrepareOptionEnum nextPrepare = PrepareOptionEnum.getEnumByIndex(signal.getPrepareOption().getRightIndex());
    signal.setPrepareOption(nextPrepare);
    return true;
  }

  @Override
  boolean up() {
    SignalEntity signal = super.activePlayer.getSignal();
    PrepareOptionEnum nextPrepare = PrepareOptionEnum.getEnumByIndex(signal.getPrepareOption().getUpIndex());
    signal.setPrepareOption(nextPrepare);
    return true;
  }

  @Override
  boolean down() {
    SignalEntity signal = super.activePlayer.getSignal();
    PrepareOptionEnum nextPrepare = PrepareOptionEnum.getEnumByIndex(signal.getPrepareOption().getDownIndex());
    signal.setPrepareOption(nextPrepare);
    return true;
  }

  @Override
  boolean detail() {
    return false;
  }

  @Override
  boolean confirm() {
    PrepareOptionEnum prepare = super.activePlayer.getSignal().getPrepareOption();
    if (PrepareOptionEnum.MY_CARD_REPOSITORY.equals(prepare)) {
      super.activePlayer.getSignal().setIndex(0);
      super.activePlayer.setStage(GameStageEnum.PREPARE_MY_CARD_REPOSITORY);
      return true;
    }
    if (PrepareOptionEnum.BY_CARD.equals(prepare)) {
      super.activePlayer.getSignal().setIndex(0);
      super.activePlayer.setStage(GameStageEnum.PREPARE_BUY_CARD);
      return true;
    }

    return this.confirmPrepareOption();
  }

  @Override
  boolean cancel() {
    return false;
  }

  private boolean confirmPrepareOption() {
    PlayerEntity player = super.activePlayer;
    PrepareOptionEnum prepare = player.getSignal().getPrepareOption();
    if (PrepareOptionEnum.PROMOTE_EQUIPMENT_SLOT.equals(prepare)) {
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return false;
      }
      player.setInitEquipmentSlotSize(player.getInitEquipmentSlotSize() + prepare.getValue());
      player.setGold(wishGold);
      return true;
    }
    if (PrepareOptionEnum.PROMOTE_MAX_HP.equals(prepare)) {
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return false;
      }
      player.setMaxHp(player.getMaxHp() + prepare.getValue());
      player.setGold(wishGold);
      return true;
    }
    if (PrepareOptionEnum.PROMOTE_INIT_MP.equals(prepare)) {
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return false;
      }
      player.setInitMp(player.getInitMp() + prepare.getValue());
      player.setGold(wishGold);
      return true;
    }
    if (PrepareOptionEnum.PROMOTE_MAX_ACTION.equals(prepare)) {
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return false;
      }
      player.setMaxAction(player.getMaxAction() + prepare.getValue());
      player.setGold(wishGold);
      return true;
    }
    if (PrepareOptionEnum.PROMOTE_INIT_HAND_CARD.equals(prepare)) {
      int wishValue = player.getInitHandCardSize() + prepare.getValue();
      if (wishValue > prepare.getLimitValue()) {
        return false;
      }
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return false;
      }
      player.setInitHandCardSize(wishValue);
      player.setGold(wishGold);
      return true;
    }
    if (PrepareOptionEnum.PROMOTE_MAX_HAND_CARD_SIZE.equals(prepare)) {
      int wishValue = player.getMaxHandCardSize() + prepare.getValue();
      if (wishValue > prepare.getLimitValue()) {
        return false;
      }
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return false;
      }
      player.setMaxHandCardSize(wishValue);
      player.setGold(wishGold);
      return true;
    }
    if (PrepareOptionEnum.PROMOTE_DRAW_CARD_SIZE.equals(prepare)) {
      int wishValue = player.getDrawCardSize() + prepare.getValue();
      if (wishValue > prepare.getLimitValue()) {
        return false;
      }
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return false;
      }
      player.setDrawCardSize(wishValue);
      player.setGold(wishGold);
      return true;
    }
    return false;
  }
}
