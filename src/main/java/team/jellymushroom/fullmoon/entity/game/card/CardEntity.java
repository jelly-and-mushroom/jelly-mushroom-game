package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;

@Data
public abstract class CardEntity implements Cloneable {

  /**
   * 从0开始，卡牌编号
   */
  Integer index;

  /**
   * 卡牌名称
   */
  String name;

  /**
   * 只有实际的卡牌实例本字段才有效
   * true:临时牌 false:非临时牌
   */
  Boolean temp = false;

  public abstract CardEntity copy();
}
