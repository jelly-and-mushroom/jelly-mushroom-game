package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;

@Data
public class SignalEntity {

  /**
   * 游戏各阶段(GameStageEnum)，主控制信号索引
   * CHOOSE_ROLE: GameRoleEntity.index
   */
  private Integer index;
}
