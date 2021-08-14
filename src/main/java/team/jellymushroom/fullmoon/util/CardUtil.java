package team.jellymushroom.fullmoon.util;

import team.jellymushroom.fullmoon.entity.game.card.CardEntity;

import java.util.List;

public class CardUtil {

  private CardUtil() {
  }

  public static void add(List<CardEntity> baseList, List<CardEntity> newList) {
    if (null == newList || newList.isEmpty()) {
      return;
    }
    newList.forEach(e -> CardUtil.add(baseList, e));
  }

  public static void add(List<CardEntity> cardList, CardEntity card) {
    if (null == cardList) {
      return;
    }
    if (cardList.isEmpty()) {
      cardList.add(card);
      return;
    }
    Integer insertIndex = null;
    for (int i = 0; i < cardList.size(); i++) {
      if (cardList.get(i).getIndex() <= card.getIndex()) {
        continue;
      }
      insertIndex = i;
      break;
    }
    if (null == insertIndex) {
      cardList.add(card);
      return;
    }
    cardList.add(insertIndex, card);
  }
}
