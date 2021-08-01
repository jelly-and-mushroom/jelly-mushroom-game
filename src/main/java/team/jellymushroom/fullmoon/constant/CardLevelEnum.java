package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum CardLevelEnum {

  LEVEL_0 (0, 90, 0.8, 1),
  LEVEL_1 (1, 60, 0.8, 2),
  LEVEL_2 (2, 30, 0.8, 3);

  private Integer index;

  /**
   * 满星/无星 状态下价格
   */
  private int price;

  /**
   * 随星级下降，价格折损比例
   */
  private double priveBreakage;

  /**
   * 非随机推荐状态下，推荐权重
   */
  private int recommendSorce;

  private static Map<Integer, CardLevelEnum> VALUE_MAP = new HashMap<>(CardLevelEnum.values().length);

  static {
    for (CardLevelEnum e : CardLevelEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  CardLevelEnum(Integer index, int price, double priveBreakage, int recommendSorce) {
    this.index = index;
    this.price = price;
    this.priveBreakage = priveBreakage;
    this.recommendSorce = recommendSorce;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static CardLevelEnum getEnumByIndex(Integer index) {
    return VALUE_MAP.get(index);
  }
}
