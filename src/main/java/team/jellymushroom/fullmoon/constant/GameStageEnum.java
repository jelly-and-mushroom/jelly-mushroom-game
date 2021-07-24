package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum GameStageEnum {

  CHOOSE_ROLE (0, "角色选择"),
  CHOOSE_ROLE_DETAIL (1, "角色选择-详情"),
  CHOOSE_ROLE_CONFIRM(2, "角色选择-已确定"),

  PREPARE(3, "游戏准备"),
  PREPARE_MY_CARD_REPOSITORY(4, "游戏准备-我的牌库");

  private Integer index;

  private String name;

  private static Map<Integer, GameStageEnum> VALUE_MAP = new HashMap<>(GameStageEnum.values().length);

  static {
    for (GameStageEnum e : GameStageEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  GameStageEnum(Integer index, String name) {
    this.index = index;
    this.name = name;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static GameStageEnum getEnumByIndex(Integer index) {
    return VALUE_MAP.get(index);
  }

  /**
   * 判断当前游戏阶段
   * true-在一小局游戏中 false-未在一小局游戏中
   */
  public static boolean inGame(GameStageEnum gameStageEnum) {
    return false;
  }
}
