package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class SignalEntity {

  /**
   * 游戏各阶段(GameStageEnum)，主控制信号索引
   */
  private Integer index = 0;

  /**
   * 游戏各阶段(GameStageEnum)，卡牌列表
   */
  private List<CardEntity> cardList = new ArrayList<>();

  /**
   * 游戏各阶段(GameStageEnum)，卡牌列表2
   */
  private List<CardEntity> cardList2 = new ArrayList<>();

  /**
   * 游戏准备阶段，当前选择的选项
   */
  private PrepareOptionEnum prepareOption = PrepareOptionEnum.MY_CARD_REPOSITORY;
}
