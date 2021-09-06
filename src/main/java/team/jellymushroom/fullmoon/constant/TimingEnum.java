package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum TimingEnum {

  OBTAIN (0, "获得");

  private Integer index;

  private String name;

  private static Map<Integer, TimingEnum> VALUE_MAP = new HashMap<>(TimingEnum.values().length);

  static {
    for (TimingEnum e : TimingEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  TimingEnum(Integer index, String name) {
    this.index = index;
    this.name = name;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static TimingEnum getEnumByIndex(Integer index) {
    return VALUE_MAP.get(index);
  }
}
