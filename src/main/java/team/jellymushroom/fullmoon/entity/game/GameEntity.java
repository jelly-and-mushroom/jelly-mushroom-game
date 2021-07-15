package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一大局游戏
 * 包含恢复一局游戏的所有静态信息(即存档读档均通过操作本类实例来实现)
 */
@Data
public class GameEntity {

  /**
   * 服务端玩家
   */
  private PlayerEntity serverPlayer;

  /**
   * 客户端玩家
   */
  private PlayerEntity clientPlayer;

  /**
   * 历史小局游戏胜负情况
   * true: serverPlayer胜利
   * false: clientPlayer胜利
   */
  private List<Boolean> historyList = new ArrayList<>();
}
