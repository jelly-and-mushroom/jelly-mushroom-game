package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.ui.module.CardListModule;

@Service
public class PrepareCardListService {

  private PrepareService prepareService;

  private MainService mainService;

  public PrepareCardListService(PrepareService prepareService, MainService mainService) {
    this.prepareService = prepareService;
    this.mainService = mainService;
  }

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
      if (wishIndex / CardListModule.CARD_COLUMN < (cardListSize - 1) / CardListModule.CARD_COLUMN) {
        wishIndex = cardListSize - 1;
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
      boolean lastLine = index / CardListModule.CARD_COLUMN == (cardListSize - 1) / CardListModule.CARD_COLUMN;
      if (lastLine) {
        wishIndex = index % CardListModule.CARD_COLUMN;
      } else {
        wishIndex = cardListSize - 1;
      }
    }
    if (myself) {
      ServerControlEntity.getInstance().setPrepareCardListIndex(wishIndex);
      return;
    }
    ServerControlEntity.getInstance().setOpponentPrepareCardListIndex(wishIndex);
  }

  public boolean confirm(boolean myself) {
    int index = myself ? ServerControlEntity.getInstance().getPrepareCardListIndex() : ServerControlEntity.getInstance().getOpponentPrepareCardListIndex();
    PlayerEntity player = myself ? this.mainService.getPlayerMyself() : this.mainService.getPlayerOpponent();
    CardEntity card = player.getCardList().get(index);
    if (!(card instanceof EquipmentCardEntity)) {
      return false;
    }
    EquipmentCardEntity equipmentCard = (EquipmentCardEntity)card;
    if (prepareService.getEquipmentInSoltSize(myself) < player.getInitEquipmentSlotSize()) {
      equipmentCard.setPlace(!equipmentCard.getPlace());
      return true;
    }
    if (!equipmentCard.getPlace()) {
      return false;
    }
    equipmentCard.setPlace(false);
    return true;
  }
}
