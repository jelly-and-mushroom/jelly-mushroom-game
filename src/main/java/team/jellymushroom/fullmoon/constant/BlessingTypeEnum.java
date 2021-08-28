package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum BlessingTypeEnum {

  COMMON (0, "普通"),
  RARE (1, "稀有"),
  PANDORA (2, "潘多拉");

  private Integer index;

  private String name;

  private static Map<Integer, BlessingTypeEnum> VALUE_MAP = new HashMap<>(BlessingTypeEnum.values().length);

  static {
    for (BlessingTypeEnum e : BlessingTypeEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  BlessingTypeEnum(Integer index, String name) {
    this.index = index;
    this.name = name;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static BlessingTypeEnum getEnumByIndex(Integer index) {
    return VALUE_MAP.get(index);
  }
}
