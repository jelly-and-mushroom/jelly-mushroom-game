package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;

@Data
public class SignalEntity {

  /**
   * 游戏选择阶段，当前选择的角色
   */
  private GameRoleEntity currentChooseRole;
}
