package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 法力牌
 */
@Data
public class ManaCardEntity extends CardEntity {

  @SneakyThrows
  @Override
  public CardEntity copy() {
    return (ManaCardEntity)this.clone();
  }
}
