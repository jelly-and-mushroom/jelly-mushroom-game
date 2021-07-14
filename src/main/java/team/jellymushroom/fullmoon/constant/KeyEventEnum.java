package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum KeyEventEnum {

  LEFT (65, "左移"),
  RIGHT (68, "右移"),
  CONFIRM (10, "确定"),
  DETAIL (83, "详情"),
  CANCEL (27, "取消");

  private Integer keyCode;

  private String description;

  private static Map<Integer, KeyEventEnum> VALUE_MAP = new HashMap<>(KeyEventEnum.values().length);

  static {
    for (KeyEventEnum e : KeyEventEnum.values()) {
      VALUE_MAP.put(e.keyCode, e);
    }
  }

  KeyEventEnum(Integer keyCode, String description) {
    this.keyCode = keyCode;
    this.description = description;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static KeyEventEnum getEnumByKeyCode(Integer keyCode) {
    return VALUE_MAP.get(keyCode);
  }
}
