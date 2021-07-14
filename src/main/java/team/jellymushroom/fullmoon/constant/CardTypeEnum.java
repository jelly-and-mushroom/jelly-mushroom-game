package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum CardTypeEnum {

  ATTACK (0, "攻击牌"),
  ACTION (1, "行动牌"),
  SPELL (2, "咒术牌"),
  MANA (3, "法力牌"),
  COUNTER (4, "反制牌"),
  EQUIPMENT (5, "装备牌"),
  PRAYER (6, "祷告牌"),
  SPECIAL (7, "特殊牌");

  private Integer index;

  private String description;

  private static Map<Integer, CardTypeEnum> VALUE_MAP = new HashMap<>(CardTypeEnum.values().length);

  static {
    for (CardTypeEnum e : CardTypeEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  CardTypeEnum(Integer index, String description) {
    this.index = index;
    this.description = description;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static CardTypeEnum getEnumByIndex(Integer index) {
    return VALUE_MAP.get(index);
  }
}
