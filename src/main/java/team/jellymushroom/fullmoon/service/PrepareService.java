package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Service
public class PrepareService {

  private MainService mainService;

  public PrepareService(MainService mainService) {
    this.mainService = mainService;
  }

  public void confirm() {
    PrepareOptionEnum prepare = ServerControlEntity.getInstance().getCurrentPrepare();
    PlayerEntity player = this.mainService.getPlayerMyself();
    this.confirm(prepare, player);
  }

  public void confirmOpponent() {
    PrepareOptionEnum prepare = ServerControlEntity.getInstance().getOpponentPrepare();
    PlayerEntity player = this.mainService.getPlayerOpponent();
    this.confirm(prepare, player);
  }

  private void confirm(PrepareOptionEnum prepare, PlayerEntity player) {
    if (PrepareOptionEnum.PROMOTE_EQUIPMENT_SLOT.equals(prepare)) {
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return;
      }
      player.setInitEquipmentSlotSize(player.getInitEquipmentSlotSize() + prepare.getValue());
      player.setGold(wishGold);
      return;
    }
    if (PrepareOptionEnum.PROMOTE_MAX_HP.equals(prepare)) {
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return;
      }
      player.setMaxHp(player.getMaxHp() + prepare.getValue());
      player.setGold(wishGold);
      return;
    }
    if (PrepareOptionEnum.PROMOTE_INIT_MP.equals(prepare)) {
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return;
      }
      player.setInitMp(player.getInitMp() + prepare.getValue());
      player.setGold(wishGold);
      return;
    }
    if (PrepareOptionEnum.PROMOTE_MAX_ACTION.equals(prepare)) {
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return;
      }
      player.setMaxAction(player.getMaxAction() + prepare.getValue());
      player.setGold(wishGold);
      return;
    }
    if (PrepareOptionEnum.PROMOTE_INIT_HAND_CARD.equals(prepare)) {
      int wishValue = player.getInitHandCardSize() + prepare.getValue();
      if (wishValue > prepare.getLimitValue()) {
        return;
      }
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return;
      }
      player.setInitHandCardSize(wishValue);
      player.setGold(wishGold);
      return;
    }
    if (PrepareOptionEnum.PROMOTE_MAX_HAND_CARD_SIZE.equals(prepare)) {
      int wishValue = player.getMaxHandCardSize() + prepare.getValue();
      if (wishValue > prepare.getLimitValue()) {
        return;
      }
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return;
      }
      player.setMaxHandCardSize(wishValue);
      player.setGold(wishGold);
      return;
    }
    if (PrepareOptionEnum.PROMOTE_DRAW_CARD_SIZE.equals(prepare)) {
      int wishValue = player.getDrawCardSize() + prepare.getValue();
      if (wishValue > prepare.getLimitValue()) {
        return;
      }
      int wishGold = player.getGold() - prepare.getPrice();
      if (wishGold < 0) {
        return;
      }
      player.setDrawCardSize(wishValue);
      player.setGold(wishGold);
      return;
    }
  }
}
