package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum GameStageEnum {

  CHOOSE_ROLE (0, "选择角色"),
  CHOOSE_ROLE_DETAIL (1, "选择角色-详情"),
  CHOOSE_ROLE_CONFIRM(2, "选择角色-已确定"),

  CHOOSE_BLESSING(3, "选择祝福"),

  PREPARE(4, "游戏准备"),
  PREPARE_MY_CARD_REPOSITORY(5, "游戏准备-我的牌库"),
  PREPARE_MY_CARD_REPOSITORY_DETAIL(6, "游戏准备-我的牌库-详情"),
  PREPARE_BUY_CARD(7, "游戏准备-购买卡牌"),
  PREPARE_BUY_CARD_DETAIL(8, "游戏准备-购买卡牌-详情"),
  PREPARE_BUY_CARD_RANDOM(9, "游戏准备-购买卡牌-随机购买卡牌"),
  PREPARE_INTENSIFY_CARD(10, "游戏准备-强化卡牌"),
  PREPARE_INTENSIFY_CARD_DETAIL(11, "游戏准备-强化卡牌-详情"),
  PREPARE_DELETE_CARD(12, "游戏准备-删除卡牌"),
  PREPARE_DELETE_CARD_DETAIL(13, "游戏准备-删除卡牌-详情"),
  PREPARE_MY_BLESSING(14, "游戏准备-受到祝福"),
  PREPARE_DONE_CONFIRM(15, "游戏准备-准备确认");

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
    if (GameStageEnum.PREPARE_DONE_CONFIRM.equals(gameStageEnum)) {
      return true;
    }
    return false;
  }
}
