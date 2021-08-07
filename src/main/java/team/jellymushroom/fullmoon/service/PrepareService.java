package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;

import java.util.List;

@Service
public class PrepareService {

  private MainService mainService;

  public PrepareService(MainService mainService) {
    this.mainService = mainService;
  }

  public int getEquipmentInSoltSize(boolean myself) {
    int result = 0;
    PlayerEntity player = myself ? this.mainService.getPlayerMyself() : this.mainService.getPlayerOpponent();
    List<CardEntity> cardList = player.getCardList();
    for (CardEntity card : cardList) {
      if (!(card instanceof EquipmentCardEntity)) {
        continue;
      }
      EquipmentCardEntity equipmentCard = (EquipmentCardEntity)card;
      if (equipmentCard.getPlace()) {
        result++;
      }
    }
    return result;
  }
}
