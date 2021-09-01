package team.jellymushroom.fullmoon.entity;

import lombok.Getter;
import team.jellymushroom.fullmoon.constant.GenreEnum;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.SpecialCardEntity;

import java.util.*;

@Getter
public class CardRecommendEntity {

  /**
   * key: CardEntity.index
   * value: 得分
   */
  private Map<Integer, Integer> cardScoreMap = new LinkedHashMap<>();

  private List<Integer> cardList = new ArrayList<>();

  private Integer totalScore = 0;

  public CardRecommendEntity(Map<Integer, Integer> genreScoreMap, List<CardEntity> cardList, Integer roleIndex) {
    for (CardEntity card : cardList) {
      if (!card.getIsValid()) {
        continue;
      }
      if (card instanceof SpecialCardEntity) {
        continue;
      }
      if (!card.getatable(roleIndex)) {
        continue;
      }
      this.add(card.getIndex(), card.getLevel().getRecommendScore());
      List<GenreEnum> genreList = card.getGenreList();
      for (GenreEnum cardGenre : genreList) {
        if (!genreScoreMap.containsKey(cardGenre.getIndex())) {
          continue;
        }
        this.add(card.getIndex(), genreScoreMap.get(cardGenre.getIndex()));
      }
    }
  }

  private void add(Integer cardIndex, int score) {
    if (this.cardScoreMap.containsKey(cardIndex)) {
      this.cardScoreMap.put(cardIndex, this.cardScoreMap.get(cardIndex) + score);
    } else {
      this.cardScoreMap.put(cardIndex, score);
    }
    this.cardList.add(cardIndex);
    this.totalScore += score;
  }

  public Integer recommend(boolean random) {
    return random ? this.random() : this.recommend();
  }

  private Integer random() {
    if (this.cardList.isEmpty()) {
      return null;
    }
    int randomValue = new Random().nextInt(cardScoreMap.size());
    return this.cardList.get(randomValue);
  }

  /**
   * @return CardEntity.index null代表没有可推荐卡牌
   */
  private Integer recommend() {
    if (this.cardScoreMap.isEmpty()) {
      return null;
    }
    int randomValue = new Random().nextInt(totalScore);
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
