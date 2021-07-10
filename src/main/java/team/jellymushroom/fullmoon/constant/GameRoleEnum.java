package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏职业
 **/
@Getter
public enum GameRoleEnum {

  KNIGHT (0, "女骑士"),
  RANGER (1, "游侠"),
  NUN (2, "修女"),
  WITCH (3, "小女巫"),
  MAGICIAN (4, "魔术师"),
  APOTHECARY (5, "药剂师"),
  WEREWOLF (6, "狼人"),
  SOUL_HUNTER (7, "契约师"),
  MECHANIC (8, "机械师");

  /**
   * 职业序号，自0起
   */
  private Integer index;

  /**
   * 职业中文名
   */
  private String nameCn;

  private static Map<Integer, GameRoleEnum> VALUE_MAP = new HashMap<>(GameRoleEnum.values().length);

  static {
    for (GameRoleEnum e : GameRoleEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }


  GameRoleEnum(Integer index, String nameCn) {
    this.index = index;
    this.nameCn = nameCn;
  }

  /**
   * 若入参是本枚举某实例的value值，则返回该实例
   * 若未找到，则返回null
   *
   * @param valueToSearch 待查找value值
   * @return 找到的枚举实例
   */
  public static GameRoleEnum getEnumByValue(Integer valueToSearch) {
    return VALUE_MAP.get(valueToSearch);
  }
}
