package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GenreEnum;
import team.jellymushroom.fullmoon.entity.BlessingRecommendEntity;
import team.jellymushroom.fullmoon.entity.CardRecommendEntity;
import team.jellymushroom.fullmoon.entity.game.BlessingEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.ui.module.CardListModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RecommendService {

  private ResourceService resourceService;

  public static final Integer GOLD_BLESSING_INDEX = 1;

  public RecommendService(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  public List<CardEntity> buyCard(PlayerEntity player) {
    // 返回卡牌数：前端能一屏显示的数量
    int resultSize = CardListModule.CARD_COLUMN * CardListModule.CARD_ROW;
    // 结果列表
    List<CardEntity> result = this.recommendCard(player, resultSize - 2);
    // 无可推荐卡牌返回空
    if (result.isEmpty()) {
      return result;
    }
    // 资源
    List<CardEntity> cardList = this.resourceService.getServiceResourceEntity().getCardList();
    // 两张特殊卡牌默认占据最后的两个位置
    result.add(cardList.get(cardList.size() - 2).copy());
    result.add(cardList.get(cardList.size() - 1).copy());
    // 返回结果列表
    return result;
  }

  public List<CardEntity> recommendCard(PlayerEntity player, int count) {
    // 结果列表
    List<CardEntity> result = new ArrayList<>(count);
    // 资源
    List<CardEntity> cardList = this.resourceService.getServiceResourceEntity().getCardList();
    Map<Integer, CardEntity> cardMap = this.resourceService.getServiceResourceEntity().getCardMap();
    // 卡牌推荐实体
    Map<Integer, Integer> genreScoreMap = this.calculateGenreScore(player.getCardList(), player.getBlessingList());
    CardRecommendEntity cardRecommend = new CardRecommendEntity(genreScoreMap, cardList, player.getGameRoleEntity().getIndex());
    // 推荐
    for (int i = 0; i < count; i++) {
      Integer recommendCardIndex = cardRecommend.recommend(false);
      if (null == recommendCardIndex) {
        break;
      }
      result.add(cardMap.get(recommendCardIndex).copy());
    }
    // 返回结果列表
    return result;
  }

  public List<BlessingEntity> recommendBlessing(PlayerEntity player, int count) {
    // 结果列表
    List<BlessingEntity> result = new ArrayList<>(count);
    // 资源
    List<BlessingEntity> blessingList = this.resourceService.getServiceResourceEntity().getBlessingList();
    Map<Integer, BlessingEntity> blessingMap = this.resourceService.getServiceResourceEntity().getBlessingMap();
    // 祝福推荐实体
    Map<Integer, Integer> genreScoreMap = this.calculateGenreScore(player.getCardList(), player.getBlessingList());
    BlessingRecommendEntity blessingRecommend = new BlessingRecommendEntity(genreScoreMap, blessingList, player.getGameRoleEntity().getIndex(), player.getBlessingList());
    // 推荐
    List<Integer> blessingIndexList = blessingRecommend.recommend(count - 1);
    blessingIndexList.add(RecommendService.GOLD_BLESSING_INDEX);
    blessingIndexList.forEach(e -> result.add(blessingMap.get(e).copy()));
    // 返回结果列表
    return result;
  }

  /**
   * key: GenreEnum.index
   * value: 得分(对应流派卡牌/祝福 得分)
   */
  private Map<Integer, Integer> calculateGenreScore(List<CardEntity> cardList, List<BlessingEntity> blessingList) {
    Map<Integer, Integer> result = new HashMap<>();
    for (CardEntity card : cardList) {
      List<GenreEnum> genreList = card.getGenreList();
      for (GenreEnum genre : genreList) {
        if (!result.containsKey(genre.getIndex())) {
          result.put(genre.getIndex(), card.getLevel().getGenreScore());
          continue;
        }
        Integer value = result.get(genre.getIndex());
        result.put(genre.getIndex(), value + card.getLevel().getGenreScore());
      }
    }
    for (BlessingEntity blessing : blessingList) {
      List<GenreEnum> genreList = blessing.getGenreList();
      for (GenreEnum genre : genreList) {
        if (!result.containsKey(genre.getIndex())) {
          result.put(genre.getIndex(), blessing.getType().getGenreScore());
          continue;
        }
        Integer value = result.get(genre.getIndex());
        result.put(genre.getIndex(), value + blessing.getType().getGenreScore());
      }
    }
    return result;
  }
}
