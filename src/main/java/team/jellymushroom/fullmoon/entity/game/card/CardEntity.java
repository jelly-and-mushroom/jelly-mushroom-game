package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.GenreEnum;
import team.jellymushroom.fullmoon.constant.CardLevelEnum;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class CardEntity implements Cloneable {

  /**
   * 从0开始，卡牌编号
   */
  Integer index;

  Boolean isValid;

  /**
   * 卡牌名称
   */
  String name;

  /**
   * 只有实际的卡牌实例本字段才有效
   * true:临时牌 false:非临时牌
   */
  Boolean temp = false;

  /**
   * 可用角色列表
   */
  List<Integer> roleIndexList = new ArrayList<>();

  /**
   * 只有当本卡牌有星级的概念时，本字段才有意义
   * 从低星至高星，依次记录本卡牌星级序列中各卡牌的index
   */
  List<Integer> starList = new ArrayList<>();

  CardLevelEnum level;

  List<GenreEnum> genreList = new ArrayList<>();

  public abstract CardEntity copy();

  public boolean getatable(int roleIndex) {
    for (Integer roleGetatable : this.roleIndexList) {
      if (roleGetatable == roleIndex) {
        return true;
      }
    }
    return false;
  }

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
}
