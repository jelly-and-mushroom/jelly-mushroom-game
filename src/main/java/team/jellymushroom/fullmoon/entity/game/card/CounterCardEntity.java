package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 反制牌
 */
@Data
public class CounterCardEntity extends CardEntity {

  @SneakyThrows
  @Override
  public CardEntity copy() {
    return (CounterCardEntity)this.clone();
  }
}
