package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 行动牌
 */
@Data
public class ActionCardEntity extends CardEntity {

  @SneakyThrows
  @Override
  public CardEntity copy() {
    return (ActionCardEntity)this.clone();
  }
}
