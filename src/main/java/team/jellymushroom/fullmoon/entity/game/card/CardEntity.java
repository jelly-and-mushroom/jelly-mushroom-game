package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;

@Data
public abstract class CardEntity {

  /**
   * 从0开始，卡牌编号
   */
  Integer index;
}
