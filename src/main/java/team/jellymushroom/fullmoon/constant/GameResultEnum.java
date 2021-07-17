package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum GameResultEnum {

  LOSE (-1, "失败"),
  DRAW (0, "平局"),
  WIN(1, "胜利");

  private Integer index;

  private String name;

  private static Map<Integer, GameResultEnum> VALUE_MAP = new HashMap<>(GameResultEnum.values().length);

  static {
    for (GameResultEnum e : GameResultEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  GameResultEnum(Integer index, String name) {
    this.index = index;
    this.name = name;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static GameResultEnum getEnumByIndex(Integer index) {
    return VALUE_MAP.get(index);
  }
}
