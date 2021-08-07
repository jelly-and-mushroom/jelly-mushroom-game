package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

public class PrepareMyCardRepositoryStageHandler extends CardListStageHandler {

  public PrepareMyCardRepositoryStageHandler(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, Boolean fromLocal) {
    super(mainService, resourceService, httpTransferService, fromLocal);
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
    super.activePlayer.setStage(GameStageEnum.PREPARE_MY_CARD_REPOSITORY_DETAIL);
    return true;
  }

  @Override
  boolean confirm() {
    int cardListSize = super.activePlayer.getCardList().size();
    if (cardListSize == 0) {
      return false;
    }
    int index = super.activePlayer.getSignal().getIndex();
    CardEntity card = super.activePlayer.getCardList().get(index);
    if (!(card instanceof EquipmentCardEntity)) {
      return false;
    }
    EquipmentCardEntity equipmentCard = (EquipmentCardEntity)card;
    if (super.activePlayer.getEquipmentInSoltSize() < super.activePlayer.getInitEquipmentSlotSize()) {
      equipmentCard.setPlace(!equipmentCard.getPlace());
      return true;
    }
    if (!equipmentCard.getPlace()) {
      return false;
    }
    equipmentCard.setPlace(false);
    return true;
  }

  @Override
  boolean cancel() {
    super.activePlayer.setStage(GameStageEnum.PREPARE);
    return true;
  }
}
