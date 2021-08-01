package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.ui.module.CardListModule;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardRecommendService {

  private MainService mainService;

  private ResourceService resourceService;

  public CardRecommendService(MainService mainService, ResourceService resourceService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
  }

  public List<CardEntity> byCard() {
    // 返回卡牌数：前端能一屏显示的数量
    int resultSize = CardListModule.CARD_COLUMN * CardListModule.CARD_ROW;
    // 结果列表
    List<CardEntity> result = new ArrayList<>(resultSize);
    // 卡牌列表
    List<CardEntity> cardList = this.resourceService.getServiceResourceEntity().getCardList();
    // 除特殊卡牌外，实际需推荐的卡牌数量
    int recommendSize = resultSize - 2;
    // 两张特殊卡牌默认占据最后的两个位置
    result.add(cardList.get(cardList.size() - 2));
    result.add(cardList.get(cardList.size() - 1));
    // 返回结果列表
    return result;
  }
}
