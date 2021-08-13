package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.CardTypeEnum;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.service.StageHandlerService;

/**
 * PREPARE_BUY_CARD
 */
public class PrepareBuyCardStageHandler extends CardListStageHandler {

  public PrepareBuyCardStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean left() {
    int cardListSize = super.activePlayer.getSignal().getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveLeft(cardListSize);
    return true;
  }

  @Override
  boolean right() {
    int cardListSize = super.activePlayer.getSignal().getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveRight(cardListSize);
    return true;
  }

  @Override
  boolean up() {
    int cardListSize = super.activePlayer.getSignal().getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveUp(cardListSize);
    return true;
  }

  @Override
  boolean down() {
    int cardListSize = super.activePlayer.getSignal().getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveDown(cardListSize);
    return true;
  }

  @Override
  boolean detail() {
    int cardListSize = super.activePlayer.getSignal().getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.activePlayer.setStage(GameStageEnum.PREPARE_BUY_CARD_DETAIL);
    return true;
  }

  @Override
  boolean confirm() {
    int cardListSize = super.activePlayer.getSignal().getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    CardEntity card = super.activePlayer.getSignal().getCardList().get(super.activePlayer.getSignal().getIndex());
    int wishGold = super.activePlayer.getGold() - card.getPrice();
    if (wishGold < 0) {
      return false;
    }
    super.activePlayer.setGold(wishGold);
    if (card.getIndex().equals(CardTypeEnum.SPECIAL.getIndexRangeBegin())) {
      super.activePlayer.getSignal().setCardList(super.stageHandlerService.getCardRecommendService().buyCard(super.activePlayer));
      return true;
    }
    return false;
  }
}
