package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum PrepareEnum {
  // 第一行
  MY_CARD_REPOSITORY (0, "我的牌库", -1, -1, -1, 1, 1, 9, 2),
  MY_BLESSING (1, "受到祝福", 0, -1, -1,  0, 0, 11, 3),
  // 第二行
  BY_CARD (2, "购买卡牌", 0, -1, -1,  3, 3, 0, 4),
  BY_BLESSING (3, "购买祝福", 0, -1, -1,  2, 2, 1, 5),
  // 第三行
  INTENSIFY_CARD (4, "强化卡牌", 0, -1, -1,  5, 5, 2, 6),
  DELETE_CARD (5, "删除卡牌", 0, -1, -1,  4, 4, 3, 8),
  // 第四行
  PROMOTE_MAX_HP (6, "生命上限+", 20, 9, -1, 8, 7, 4, 9),
  PROMOTE_INIT_MP (7, "初始魔法值+", 20, 6, -1, 6, 8, 4, 10),
  PROMOTE_MAX_ACTION (8, "行动力上限+", 50, 1, -1, 7, 6, 5, 11),
  // 第五行
  PROMOTE_INIT_HAND_CARD (9, "初始手牌+", 50, 1, 5, 11, 10, 6, 0),
  PROMOTE_MAX_HAND_CARD_SIZE (10, "手牌上限+", 50, 1, 5, 9, 11, 7, 0),
  PROMOTE_DRAW_CARD_SIZE (11, "抽牌+", 50, 1, 3, 10, 9, 8, 1);

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
   * -1 表示无意义
   */
  private Integer leftIndex;

  /**
   * 在本选项上时，按下 右 会到达的选项
   * -1 表示无意义
   */
  private Integer rightIndex;

  /**
   * 在本选项上时，按下 上 会到达的选项
   * -1 表示无意义
   */
  private Integer upIndex;

  /**
   * 在本选项上时，按下 下 会到达的选项
   * -1 表示无意义
   */
  private Integer downIndex;

  private static Map<Integer, PrepareEnum> VALUE_MAP = new HashMap<>(PrepareEnum.values().length);

  static {
    for (PrepareEnum e : PrepareEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  PrepareEnum(Integer index, String description, Integer price, Integer value, Integer limitValue, Integer leftIndex, Integer rightIndex, Integer upIndex, Integer downIndex) {
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
  public static PrepareEnum getEnumByKeyCode(Integer keyCode) {
    return VALUE_MAP.get(keyCode);
  }
}
