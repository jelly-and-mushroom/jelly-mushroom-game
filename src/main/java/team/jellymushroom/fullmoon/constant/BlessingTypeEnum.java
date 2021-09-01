package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum BlessingTypeEnum {

  COMMON (0, "普通", 3, 1),
  RARE (1, "稀有", 1, 3),
  PANDORA (2, "潘多拉", 2, 2);

  private Integer index;

  private String name;

  /**
   * 非随机推荐状态下，推荐权重
   */
  private int recommendScore;

  /**
   * 对所属流派贡献的权重
   */
  private int genreScore;

  private static Map<Integer, BlessingTypeEnum> VALUE_MAP = new HashMap<>(BlessingTypeEnum.values().length);

  static {
    for (BlessingTypeEnum e : BlessingTypeEnum.values()) {
      VALUE_MAP.put(e.index, e);
    }
  }

  BlessingTypeEnum(Integer index, String name, int recommendScore, int genreScore) {
    this.index = index;
    this.name = name;
    this.recommendScore = recommendScore;
    this.genreScore = genreScore;
  }

  /**
   * 若入参为null,返回null
   * 若未找到对应枚举实例，返回null
   */
  public static BlessingTypeEnum getEnumByIndex(Integer index) {
    return VALUE_MAP.get(index);
  }
}
