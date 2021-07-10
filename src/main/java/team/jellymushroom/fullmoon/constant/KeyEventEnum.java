package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 键盘按键
 **/
@Getter
public enum KeyEventEnum {

  LEFT (65, "左移"),
  RIGHT (68, "右移"),
  CONFIRM (10, "确定"),
  DETAIL (83, "详细信息");

  /**
   * 职业序号，自0起
   */
  private Integer keyCode;

  /**
   * 描述
   */
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
   * 若入参是本枚举某实例的value值，则返回该实例
   * 若未找到，则返回null
   *
   * @param valueToSearch 待查找value值
   * @return 找到的枚举实例
   */
  public static KeyEventEnum getEnumByValue(Integer valueToSearch) {
    return VALUE_MAP.get(valueToSearch);
  }
}
