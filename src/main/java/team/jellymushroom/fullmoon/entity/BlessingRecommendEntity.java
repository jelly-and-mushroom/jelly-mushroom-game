package team.jellymushroom.fullmoon.entity;

import lombok.Getter;
import team.jellymushroom.fullmoon.constant.GenreEnum;
import team.jellymushroom.fullmoon.entity.game.GameBlessingEntity;
import team.jellymushroom.fullmoon.service.RecommendService;

import java.util.*;

@Getter
public class BlessingRecommendEntity {

  /**
   * key: GameBlessingEntity.index
   * value: 得分
   */
  private Map<Integer, Integer> blessingScoreMap = new LinkedHashMap<>();

  private Integer totalScore = 0;

  public BlessingRecommendEntity(Map<Integer, Integer> genreScoreMap, List<GameBlessingEntity> allBlessingList, Integer roleIndex, List<GameBlessingEntity> obtainedBlessingList) {
    Set<Integer> obtainedBlessingIndexSet = new HashSet<>();
    obtainedBlessingList.forEach(e -> obtainedBlessingIndexSet.add(e.getIndex()));
    for (GameBlessingEntity blessing : allBlessingList) {
      if (!blessing.getIsValid()) {
        continue;
      }
      if (!blessing.getRepeatable() && obtainedBlessingList.contains(blessing.getIndex())) {
        continue;
      }
      if (blessing.getIndex().intValue() == RecommendService.GOLD_BLESSING_INDEX.intValue()) {
        continue;
      }
      if (!blessing.getatable(roleIndex)) {
        continue;
      }
      this.add(blessing.getIndex(), blessing.getType().getRecommendScore());
      List<GenreEnum> genreList = blessing.getGenreList();
      for (GenreEnum cardGenre : genreList) {
        if (!genreScoreMap.containsKey(cardGenre.getIndex())) {
          continue;
        }
        this.add(blessing.getIndex(), genreScoreMap.get(cardGenre.getIndex()));
      }
    }
  }

  private void add(Integer blessingIndex, int score) {
    if (this.blessingScoreMap.containsKey(blessingIndex)) {
      this.blessingScoreMap.put(blessingIndex, this.blessingScoreMap.get(blessingIndex) + score);
    } else {
      this.blessingScoreMap.put(blessingIndex, score);
    }
    this.totalScore += score;
  }

  public List<Integer> recommend(int count) {
    List<Integer> result = new ArrayList<>();
    if (count <= 0) {
      return result;
    }
    if (this.blessingScoreMap.isEmpty()) {
      return result;
    }
    Map<Integer, Integer> currentMap = new HashMap<>(this.blessingScoreMap);
    int currentTotalScore = this.totalScore;
    for (int i = 0; i < count; i++) {
      Map.Entry<Integer, Integer> entry = this.recommend(currentMap, currentTotalScore);
      if (null == entry) {
        break;
      }
      result.add(entry.getKey());
      currentMap.remove(entry.getKey());
      currentTotalScore -= entry.getValue();
    }
    return result;
  }

  private Map.Entry<Integer, Integer> recommend(Map<Integer, Integer> currentMap, int currentTotalScore) {
    if (currentMap.isEmpty()) {
      return null;
    }
    int randomValue = new Random().nextInt(currentTotalScore);
    int start = 0;
    int end = -1;
    for (Map.Entry<Integer, Integer> entry : currentMap.entrySet()) {
      start = end + 1;
      end = start + entry.getValue() - 1;
      if (randomValue >= start && randomValue <= end) {
        return entry;
      }
    }
    return null;
  }
}
