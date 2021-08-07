package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.entity.game.SignalEntity;
import team.jellymushroom.fullmoon.service.StageHandlerService;
import team.jellymushroom.fullmoon.ui.module.CardListModule;

public abstract class CardListStageHandler extends StageHandler {

  CardListStageHandler(StageHandlerService stageHandlerService, Boolean fromLocal) {
    super(stageHandlerService, fromLocal);
  }

  void moveLeft(int cardListSize) {
    SignalEntity signal = super.activePlayer.getSignal();
    int index = signal.getIndex();
    int wishIndex = index - 1;
    if (index % CardListModule.CARD_COLUMN == 0) {
      wishIndex = index + 3;
      if (wishIndex >= cardListSize) {
        wishIndex = cardListSize - 1;
      }
    }
    signal.setIndex(wishIndex);
  }

  void moveRight(int cardListSize) {
    SignalEntity signal = super.activePlayer.getSignal();
    int index = signal.getIndex();
    int wishIndex = index + 1;
    if (index % CardListModule.CARD_COLUMN == CardListModule.CARD_COLUMN - 1) {
      wishIndex = index - (CardListModule.CARD_COLUMN - 1);
    } else {
      if (wishIndex >= cardListSize) {
        wishIndex = CardListModule.CARD_COLUMN * (index / CardListModule.CARD_COLUMN);
      }
    }
    signal.setIndex(wishIndex);
  }

  void moveUp(int cardListSize) {
    SignalEntity signal = super.activePlayer.getSignal();
    int index = signal.getIndex();
    int wishIndex = index - CardListModule.CARD_COLUMN;
    if (wishIndex < 0) {
      int lastLineFirstIndex = (cardListSize / CardListModule.CARD_COLUMN) * CardListModule.CARD_COLUMN;
      wishIndex = lastLineFirstIndex + index;
      if (wishIndex >= cardListSize) {
        wishIndex = wishIndex - CardListModule.CARD_COLUMN;
      }
      if (wishIndex / CardListModule.CARD_COLUMN < (cardListSize - 1) / CardListModule.CARD_COLUMN) {
        wishIndex = cardListSize - 1;
      }
    }
    signal.setIndex(wishIndex);
  }

  void moveDown(int cardListSize) {
    SignalEntity signal = super.activePlayer.getSignal();
    int index = signal.getIndex();
    int wishIndex = index + CardListModule.CARD_COLUMN;
    if (wishIndex >= cardListSize) {
      boolean lastLine = index / CardListModule.CARD_COLUMN == (cardListSize - 1) / CardListModule.CARD_COLUMN;
      if (lastLine) {
        wishIndex = index % CardListModule.CARD_COLUMN;
      } else {
        wishIndex = cardListSize - 1;
      }
    }
    signal.setIndex(wishIndex);
  }
}
