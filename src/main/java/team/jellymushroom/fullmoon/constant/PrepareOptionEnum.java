package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum PrepareOptionEnum {
  // 第一行
  MY_CARD_REPOSITORY (0, "我的牌库", -1, -1, -1, 1, 1, 13, 2),
  MY_BLESSING (1, "受到祝福", -1, -1, -1,  0, 0, 13, 3),
  // 第二行
  BY_CARD (2, "购买卡牌", -1, -1, -1,  3, 3, 0, 4),
  BY_BLESSING (3, "购买祝福", -1, -1, -1,  2, 2, 1, 6),
  // 第三行
  INTENSIFY_CARD (4, "强化卡牌", 30, -1, -1,  6, 5, 2, 7),
  DELETE_CARD (5, "删除卡牌", 50, -1, -1,  4, 6, 2, 8),
  PROMOTE_EQUIPMENT_SLOT (6, "装备槽+", 50, 1, -1, 5, 4, 3, 9),
  // 第四行
  PROMOTE_MAX_HP (7, "生命值上限+", 20, 9, -1, 9, 8, 4, 10),
  PROMOTE_INIT_MP (8, "初始魔法值+", 20, 6, -1, 7, 9, 5, 11),
  PROMOTE_MAX_ACTION (9, "行动力上限+", 50, 1, -1, 8, 7, 6, 12),
  // 第五行
  PROMOTE_INIT_HAND_CARD (10, "初始手牌+", 50, 1, 5, 12, 11, 7, 13),
  PROMOTE_MAX_HAND_CARD_SIZE (11, "手牌上限+", 50, 1, 5, 10, 12, 8, 13),
  PROMOTE_DRAW_CARD_SIZE (12, "抽取牌数+", 99, 1, 3, 11, 10, 9, 13),
  // 第六行
  DONE (13, "准备完成", -1, -1, -1, 13, 13, 11, 0);

  private Integer index;

  private String description;

  /**
   * 本选项价格
   * -1 表示无意义
   */
  private Integer price;

  /**
   * 本选项提升数值
   * -1 表示无意义
   */
  private Integer value;

  /**
   * 本选项提升数值允许的上限
   * -1 表示无意义
   */
  private Integer limitValue;

  /**
   * 在本选项上时，按下 左 会到达的选项
   */
  private Integer leftIndex;

  /**
   * 在本选项上时，按下 右 会到达的选项
   */
  private Integer rightIndex;

  /**
   * 在本选项上时，按下 上 会到达的选项
   */
  private Integer upIndex;

  /**
   * 在本选项上时，按下 下 会到达的选项
   */
  private Integer downIndex;

  private static Map<Integer, PrepareOptionEnum> VALUE_MAP = new HashMap<>(PrepareOptionEnum.values().length);

  static {
    for (PrepareOptionEnum e : PrepareOptionEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  PrepareOptionEnum(Integer index, String description, Integer price, Integer value, Integer limitValue, Integer leftIndex, Integer rightIndex, Integer upIndex, Integer downIndex) {
    this.index = index;
    this.description = description;
    this.price = price;
    this.value = value;
    this.limitValue = limitValue;
    this.leftIndex = leftIndex;
    this.rightIndex = rightIndex;
    this.upIndex = upIndex;
    this.downIndex = downIndex;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static PrepareOptionEnum getEnumByKeyCode(Integer keyCode) {
    return VALUE_MAP.get(keyCode);
  }
}
