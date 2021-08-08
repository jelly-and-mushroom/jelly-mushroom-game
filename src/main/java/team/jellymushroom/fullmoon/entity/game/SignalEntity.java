package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;

@Data
public class SignalEntity {

  /**
   * 游戏各阶段(GameStageEnum)，主控制信号索引
   * CHOOSE_ROLE CHOOSE_ROLE_DETAIL CHOOSE_ROLE_CONFIRM: GameRoleEntity.index
   * PREPARE_MY_CARD_REPOSITORY PREPARE_MY_CARD_REPOSITORY_DETAIL PREPARE_BY_CARD: 卡牌在列表中的位置索引
   */
  private Integer index = 0;

  /**
   * 游戏准备阶段，当前选择的选项
   */
  private PrepareOptionEnum prepareOption = PrepareOptionEnum.MY_CARD_REPOSITORY;
}
