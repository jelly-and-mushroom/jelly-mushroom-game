package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.SignalEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.ui.module.CardListModule;

@Service
public class PrepareCardListService {

  private MainService mainService;

  public PrepareCardListService(MainService mainService) {
    this.mainService = mainService;
  }

  public void moveLeft(int cardListSize, boolean myself) {
    SignalEntity signal = myself ? this.mainService.getPlayerMyself().getSignal() : this.mainService.getPlayerOpponent().getSignal();
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

  public void moveRight(int cardListSize, boolean myself) {
    SignalEntity signal = myself ? this.mainService.getPlayerMyself().getSignal() : this.mainService.getPlayerOpponent().getSignal();
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

  public void moveUp(int cardListSize, boolean myself) {
    SignalEntity signal = myself ? this.mainService.getPlayerMyself().getSignal() : this.mainService.getPlayerOpponent().getSignal();
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

  public void moveDown(int cardListSize, boolean myself) {
    SignalEntity signal = myself ? this.mainService.getPlayerMyself().getSignal() : this.mainService.getPlayerOpponent().getSignal();
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

  public boolean confirm(boolean myself) {
    PlayerEntity player = myself ? this.mainService.getPlayerMyself() : this.mainService.getPlayerOpponent();
    int index = player.getSignal().getIndex();
    CardEntity card = player.getCardList().get(index);
    if (!(card instanceof EquipmentCardEntity)) {
      return false;
    }
    EquipmentCardEntity equipmentCard = (EquipmentCardEntity)card;
    if (player.getEquipmentInSoltSize() < player.getInitEquipmentSlotSize()) {
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
