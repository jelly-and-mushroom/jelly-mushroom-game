package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum EffectiveFromEnum {

  CARD (0, "卡牌"),
  BLESSING (1, "祝福");

  private Integer index;

  private String name;

  private static Map<Integer, EffectiveFromEnum> VALUE_MAP = new HashMap<>(EffectiveFromEnum.values().length);

  static {
    for (EffectiveFromEnum e : EffectiveFromEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  EffectiveFromEnum(Integer index, String name) {
    this.index = index;
    this.name = name;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static EffectiveFromEnum getEnumByIndex(Integer index) {
    return VALUE_MAP.get(index);
  }
}
