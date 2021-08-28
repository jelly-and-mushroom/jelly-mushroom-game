package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.service.StageHandlerService;

import java.util.List;

/**
 * PREPARE_DELETE_CARD
 */
public class PrepareDeleteCardStageHandler extends CardListStageHandler {

  public static final Integer PRICE = 50;

  public PrepareDeleteCardStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean left() {
    int cardListSize = super.activePlayer.getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveLeft(cardListSize);
    return true;
  }

  @Override
  boolean right() {
    int cardListSize = super.activePlayer.getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveRight(cardListSize);
    return true;
  }

  @Override
  boolean up() {
    int cardListSize = super.activePlayer.getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveUp(cardListSize);
    return true;
  }

  @Override
  boolean down() {
    int cardListSize = super.activePlayer.getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveDown(cardListSize);
    return true;
  }

  @Override
  boolean detail() {
    int cardListSize = super.activePlayer.getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.activePlayer.setStage(GameStageEnum.PREPARE_DELETE_CARD_DETAIL);
    return true;
  }

  @Override
  boolean confirm() {
    List<CardEntity> cardList = super.activePlayer.getCardList();
    if (cardList.size() == 0) {
      return false;
    }
    int wishGold = super.activePlayer.getGold() - PrepareDeleteCardStageHandler.PRICE;
    if (wishGold < 0) {
      return false;
    }
    int index = super.activePlayer.getSignal().getIndex();
    super.activePlayer.getSignal().setIndex(0);
    cardList.remove(index);
    super.activePlayer.setGold(wishGold);
    return true;
  }
}
