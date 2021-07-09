package team.jellymushroom.jmgame.core.entity.game;

import lombok.Data;

/**
 * 一局游戏
 * 对一局游戏进行存档实际即为保存本类实例，因此本类实例应包含一局游戏所有的静态信息
 * 一局游戏包含多小局游戏，本类实例记录的是最新一个小局最新的静态状态(如有需要，历史小局的数据也会记录)
 */
@Data
public class GameEntity {

  /**
   * 服务端用户
   */
  PlayerEntity serverPlayer;

  /**
   * 客户端用户
   */
  PlayerEntity clientPlayer;
}
