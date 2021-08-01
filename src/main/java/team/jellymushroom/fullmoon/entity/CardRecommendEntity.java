package team.jellymushroom.fullmoon.entity;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Getter
public class CardRecommendEntity {

  /**
   * key: CardEntity.index
   * value: 得分
   */
  private Map<Integer, Integer> cardScoreMap = new LinkedHashMap<>();

  private Integer totalScore = 0;

  public void add(Integer cardIndex, int score) {
    if (this.cardScoreMap.containsKey(cardIndex)) {
      this.cardScoreMap.put(cardIndex, this.cardScoreMap.get(cardIndex) + score);
    } else {
      this.cardScoreMap.put(cardIndex, score);
    }
    this.totalScore += score;
  }

  /**
   * @return CardEntity.index null代表没有可推荐卡牌
   */
  public Integer recommend() {
    if (this.totalScore == 0) {
      return null;
    }
    Random random = new Random();
    int randomValue = random.nextInt(totalScore);
    int start = 0;
    int end = -1;
    for (Map.Entry<Integer, Integer> entry : this.cardScoreMap.entrySet()) {
      start = end + 1;
      end = start + entry.getValue() - 1;
      if (randomValue >= start && randomValue <= end) {
        return entry.getKey();
      }
    }
    return null;
  }
}
