package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.ui.module.CardListModule;

@Service
public class PrepareCardListService {

  public void moveLeft(int cardListSize, boolean myself) {
    int index = myself ? ServerControlEntity.getInstance().getPrepareCardListIndex() : ServerControlEntity.getInstance().getOpponentPrepareCardListIndex();
    int wishIndex = index - 1;
    if (index % CardListModule.CARD_COLUMN == 0) {
      wishIndex = index + 3;
      if (wishIndex >= cardListSize) {
        wishIndex = cardListSize - 1;
      }
    }
    if (myself) {
      ServerControlEntity.getInstance().setPrepareCardListIndex(wishIndex);
      return;
    }
    ServerControlEntity.getInstance().setOpponentPrepareCardListIndex(wishIndex);
  }

  public void moveRight(int cardListSize, boolean myself) {
    int index = myself ? ServerControlEntity.getInstance().getPrepareCardListIndex() : ServerControlEntity.getInstance().getOpponentPrepareCardListIndex();
    int wishIndex = index + 1;
    if (index % CardListModule.CARD_COLUMN == CardListModule.CARD_COLUMN - 1) {
      wishIndex = index - (CardListModule.CARD_COLUMN - 1);
    } else {
      if (wishIndex >= cardListSize) {
        wishIndex = CardListModule.CARD_COLUMN * (index / CardListModule.CARD_COLUMN);
      }
    }
    if (myself) {
      ServerControlEntity.getInstance().setPrepareCardListIndex(wishIndex);
      return;
    }
    ServerControlEntity.getInstance().setOpponentPrepareCardListIndex(wishIndex);
  }

  public void moveUp(int cardListSize, boolean myself) {
    int index = myself ? ServerControlEntity.getInstance().getPrepareCardListIndex() : ServerControlEntity.getInstance().getOpponentPrepareCardListIndex();
    int wishIndex = index - CardListModule.CARD_COLUMN;
    if (wishIndex < 0) {
      int lastLineFirstIndex = (cardListSize / CardListModule.CARD_COLUMN) * CardListModule.CARD_COLUMN;
      wishIndex = lastLineFirstIndex + index;
      if (wishIndex >= cardListSize) {
        wishIndex = wishIndex - CardListModule.CARD_COLUMN;
      }
    }
    if (myself) {
      ServerControlEntity.getInstance().setPrepareCardListIndex(wishIndex);
      return;
    }
    ServerControlEntity.getInstance().setOpponentPrepareCardListIndex(wishIndex);
  }

  public void moveDown(int cardListSize, boolean myself) {
    int index = myself ? ServerControlEntity.getInstance().getPrepareCardListIndex() : ServerControlEntity.getInstance().getOpponentPrepareCardListIndex();
    int wishIndex = index + CardListModule.CARD_COLUMN;
    if (wishIndex >= cardListSize) {
      wishIndex = index % CardListModule.CARD_COLUMN;
    }
    if (myself) {
      ServerControlEntity.getInstance().setPrepareCardListIndex(wishIndex);
      return;
    }
    ServerControlEntity.getInstance().setOpponentPrepareCardListIndex(wishIndex);
  }
}
