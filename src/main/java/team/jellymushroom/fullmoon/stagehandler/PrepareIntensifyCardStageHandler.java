package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.service.StageHandlerService;
import team.jellymushroom.fullmoon.util.CardUtil;

import java.util.List;

/**
 * PREPARE_INTENSIFY_CARD
 */
public class PrepareIntensifyCardStageHandler extends CardListStageHandler {

  public static final Integer PRICE = 30;

  public PrepareIntensifyCardStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  @Override
  boolean left() {
    int cardListSize = super.activePlayer.getIntensifyCardIndexList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveLeft(cardListSize);
    return true;
  }

  @Override
  boolean right() {
    int cardListSize = super.activePlayer.getIntensifyCardIndexList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveRight(cardListSize);
    return true;
  }

  @Override
  boolean up() {
    int cardListSize = super.activePlayer.getIntensifyCardIndexList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveUp(cardListSize);
    return true;
  }

  @Override
  boolean down() {
    int cardListSize = super.activePlayer.getIntensifyCardIndexList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.moveDown(cardListSize);
    return true;
  }

  @Override
  boolean detail() {
    int cardListSize = super.activePlayer.getIntensifyCardIndexList().size();
    if (cardListSize == 0) {
      return false;
    }
    super.activePlayer.setStage(GameStageEnum.PREPARE_INTENSIFY_CARD_DETAIL);
    return true;
  }

  @Override
  boolean confirm() {
    List<Integer> cardList = super.activePlayer.getIntensifyCardIndexList();
    int cardListSize = cardList.size();
    if (cardListSize == 0) {
      return false;
    }
    int wishGold = super.activePlayer.getGold() - PrepareIntensifyCardStageHandler.PRICE;
    if (wishGold < 0) {
      return false;
    }
    int index = cardList.get(super.activePlayer.getSignal().getIndex());
    super.activePlayer.getSignal().setIndex(0);
    CardEntity card = super.activePlayer.getCardList().get(index);
    super.activePlayer.getCardList().remove(index);
    CardUtil.add(super.activePlayer.getCardList(), super.stageHandlerService.getResourceService().getServiceResourceEntity().getCardMap().get(card.getNextStarCardIndex()).copy());
    super.activePlayer.setGold(wishGold);
    return true;
  }
}
