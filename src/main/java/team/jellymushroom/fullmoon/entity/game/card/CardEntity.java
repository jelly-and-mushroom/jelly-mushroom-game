package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.CardGenreEnum;
import team.jellymushroom.fullmoon.constant.CardLevelEnum;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class CardEntity implements Cloneable {

  /**
   * 从0开始，卡牌编号
   */
  Integer index;

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
  private List<Integer> roleIndexList = new ArrayList<>();

  /**
   * 只有当本卡牌有星级的概念时，本字段才有意义
   * 从低星至高星，依次记录本卡牌星级序列中各卡牌的index
   */
  private List<Integer> starList = new ArrayList<>();

  private CardLevelEnum level;

  private List<CardGenreEnum> genreList = new ArrayList<>();

  public abstract CardEntity copy();
}
