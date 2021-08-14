package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.CardGenreEnum;
import team.jellymushroom.fullmoon.entity.CardRecommendEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.SpecialCardEntity;
import team.jellymushroom.fullmoon.ui.module.CardListModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CardRecommendService {

  private ResourceService resourceService;

  public CardRecommendService(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  public List<CardEntity> buyCard(PlayerEntity player) {
    // 返回卡牌数：前端能一屏显示的数量
    int resultSize = CardListModule.CARD_COLUMN * CardListModule.CARD_ROW;
    // 结果列表
    List<CardEntity> result = this.recommend(player, resultSize - 2);
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

  public List<CardEntity> recommend(PlayerEntity player, int count) {
    // 结果列表
    List<CardEntity> result = new ArrayList<>(count);
    // 资源
    List<CardEntity> cardList = this.resourceService.getServiceResourceEntity().getCardList();
    Map<Integer, CardEntity> cardMap = this.resourceService.getServiceResourceEntity().getCardMap();
    // 卡牌推荐实体
    Map<Integer, Integer> obtainedCardGenreScoreMap = this.calculateCardGenreScore(player.getCardList());
    CardRecommendEntity cardRecommend = this.calculateRecommendCardScore(obtainedCardGenreScoreMap, cardList, player.getGameRoleEntity().getIndex());
    // 推荐
    for (int i = 0; i < count; i++) {
      Integer recommendCardIndex = cardRecommend.recommend();
      if (null == recommendCardIndex) {
        break;
      }
      result.add(cardMap.get(recommendCardIndex).copy());
    }
    // 返回结果列表
    return result;
  }

  /**
   * key: CardGenreEnum.index
   * value: 得分(对应流派卡牌张数)
   */
  private Map<Integer, Integer> calculateCardGenreScore(List<CardEntity> cardList) {
    Map<Integer, Integer> result = new HashMap<>();
    for (CardEntity card : cardList) {
      List<CardGenreEnum> genreList = card.getGenreList();
      for (CardGenreEnum cardGenre : genreList) {
        if (!result.containsKey(cardGenre.getIndex())) {
          result.put(cardGenre.getIndex(), 1);
          continue;
        }
        Integer value = result.get(cardGenre.getIndex());
        result.put(cardGenre.getIndex(), value + 1);
      }
    }
    return result;
  }

  private CardRecommendEntity calculateRecommendCardScore(Map<Integer, Integer> cardGenreScoreMap, List<CardEntity> cardList, int roleIndex) {
    CardRecommendEntity result = new CardRecommendEntity();
    for (CardEntity card : cardList) {
      if (card instanceof SpecialCardEntity) {
        continue;
      }
      if (!card.getatable(roleIndex)) {
        continue;
      }
      result.add(card.getIndex(), card.getLevel().getRecommendSorce());
      List<CardGenreEnum> genreList = card.getGenreList();
      for (CardGenreEnum cardGenre : genreList) {
        if (!cardGenreScoreMap.containsKey(cardGenre.getIndex())) {
          continue;
        }
        result.add(card.getIndex(), cardGenreScoreMap.get(cardGenre.getIndex()));
      }
    }
    return result;
  }
}
