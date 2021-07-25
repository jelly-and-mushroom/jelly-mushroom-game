package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 特殊牌
 */
@Data
public class SpecialCardEntity extends CardEntity {

  @SneakyThrows
  @Override
  public CardEntity copy() {
    return (SpecialCardEntity)this.clone();
  }
}
