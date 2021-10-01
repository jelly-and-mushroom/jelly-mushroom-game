package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.CardLevelEnum;
import team.jellymushroom.fullmoon.entity.game.EffectiveEntity;
import team.jellymushroom.fullmoon.ui.module.CardListModule;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class CardEntity extends EffectiveEntity {

  /**
   * 只有实际的卡牌实例本字段才有效
   * true:临时牌 false:非临时牌
   */
  Boolean temp = false;

  /**
   * 只有当本卡牌有星级的概念时，本字段才有意义
   * 从低星至高星，依次记录本卡牌星级序列中各卡牌的index
   */
  List<Integer> starList = new ArrayList<>();

  CardLevelEnum level;

  public abstract CardEntity copy();

  public Integer getPrice() {
    Integer result = this.level.getPrice();
    Integer star = this.getStar();
    if (null == star) {
      return result;
    }
    Integer breakTimes = this.starList.size() - star;
    for (int i = 0; i < breakTimes; i++) {
      result = (int)(this.level.getPriveBreakage() * result);
    }
    return result;
  }

  private Integer getStar() {
    if (this.starList.isEmpty()) {
      return null;
    }
    for (int i = 0; i < this.starList.size(); i++) {
      if (this.index == this.starList.get(i)) {
        return i + 1;
      }
    }
    return null;
  }

  public Integer getNextStarCardIndex() {
    if (this.starList.isEmpty()) {
      return null;
    }
    for (int i = 0; i < this.starList.size(); i++) {
      if (this.index.intValue() != this.starList.get(i)) {
        continue;
      }
      if (i != this.starList.size() - 1) {
        return this.starList.get(i + 1);
      }
    }
    return null;
  }

  public int getPrice(int price, int index, double rightCardCostRate) {
    boolean right = false;
    for (int i = 1; i < index; i++) {
      int tempIndex = i * CardListModule.CARD_COLUMN - 1;
      if (tempIndex > index) {
        break;
      }
      if (tempIndex == index) {
        right = true;
        break;
      }
    }
    if (!right) {
      return price;
    }
    return (int)(price * rightCardCostRate);
  }
}
