package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;

@Data
public class SignalEntity {

  /**
   * 游戏各阶段(GameStageEnum)，主控制信号索引
   * CHOOSE_ROLE CHOOSE_ROLE_DETAIL CHOOSE_ROLE_CONFIRM: GameRoleEntity.index
   */
  private Integer index;

  /**
   * 游戏准备阶段，当前选择的选项
   */
  private PrepareOptionEnum prepareOption;
}
