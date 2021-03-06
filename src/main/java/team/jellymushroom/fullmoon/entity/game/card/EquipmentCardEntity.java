package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 装备牌
 */
@Data
public class EquipmentCardEntity extends CardEntity {

  private Boolean place = false;

  @SneakyThrows
  @Override
  public CardEntity copy() {
    return (EquipmentCardEntity)this.clone();
  }
}
