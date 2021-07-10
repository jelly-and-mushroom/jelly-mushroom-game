package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.GameStageEnum;

/**
 * 一大局游戏
 * 包含恢复一局游戏的所有静态信息(即存档读档均通过操作本类实例来实现)
 */
@Data
public class GameEntity {

  /**
   * 玩家自身
   */
  private PlayerEntity mySelf = new PlayerEntity();

  /**
   * 对手玩家
   */
  private PlayerEntity opponent;

  /**
   * 当前本局游戏所处阶段
   */
  private GameStageEnum stage = GameStageEnum.CHOOSE_ROLE;
}
