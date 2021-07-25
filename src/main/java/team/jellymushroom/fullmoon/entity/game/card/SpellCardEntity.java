package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 咒术牌
 */
@Data
public class SpellCardEntity extends CardEntity {

  @SneakyThrows
  @Override
  public CardEntity copy() {
    return (SpellCardEntity)this.clone();
  }
}
