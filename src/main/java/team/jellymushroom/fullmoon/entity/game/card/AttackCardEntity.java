package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 攻击牌
 */
@Data
public class AttackCardEntity extends CardEntity {

  @SneakyThrows
  @Override
  public CardEntity copy() {
    return (AttackCardEntity)this.clone();
  }
}
