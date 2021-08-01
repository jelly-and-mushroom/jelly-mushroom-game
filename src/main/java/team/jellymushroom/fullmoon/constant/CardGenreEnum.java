package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum CardGenreEnum {

  EQUIPMENT (0, "装备"),
  ARMOR (1, "护甲");

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
