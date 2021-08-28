package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum CardGenreEnum {

  EQUIPMENT (0, "装备"),
  ARMOR (1, "护甲"),
  MP (2, "法力"),
  ACTION (3, "行动力"),
  PRAYER (4, "祷告"),
  FIRE (5, "火属性"),
  DODGE (6, "闪避"),
  INVALID (7, "无效化"),
  REDUCE_CONSUMPTION (8, "减耗");

  private Integer index;

  private String name;

  private static Map<Integer, CardGenreEnum> VALUE_MAP = new HashMap<>(CardGenreEnum.values().length);

  static {
    for (CardGenreEnum e : CardGenreEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  CardGenreEnum(Integer index, String name) {
    this.index = index;
    this.name = name;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static CardGenreEnum getEnumByIndex(Integer index) {
    return VALUE_MAP.get(index);
  }
}
