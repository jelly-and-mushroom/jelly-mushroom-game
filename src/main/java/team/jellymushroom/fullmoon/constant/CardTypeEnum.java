package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

@Getter
public enum CardTypeEnum {

  EQUIPMENT (0, 999, "装备牌"),
  ATTACK (1000, 1999, "攻击牌"),
  ACTION (2000, 2999, "行动牌"),
  SPELL (3000, 3999, "咒术牌"),
  MANA (4000, 4999, "法力牌"),
  COUNTER (5000, 5999, "反制牌"),
  PRAYER (6000, 6999, "祷告牌"),
  SPECIAL (7000, 7999, "特殊牌");

  /**
   * 卡牌开始索引，包含关系
   */
  private Integer indexRangeBegin;

  /**
   * 卡牌结束索引，包含关系
   */
  private Integer indexRangeEnd;

  private String description;

  CardTypeEnum(Integer indexRangeBegin, Integer indexRangeEnd, String description) {
    this.indexRangeBegin = indexRangeBegin;
    this.indexRangeEnd = indexRangeEnd;
    this.description = description;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static CardTypeEnum getEnumByIndex(Integer cardIndex) {
    if (null == cardIndex) {
      return null;
    }
    for (CardTypeEnum e : CardTypeEnum.values()) {
      if (cardIndex >= e.indexRangeBegin && cardIndex <= e.indexRangeEnd) {
        return e;
      }
    }
    return null;
  }
}
