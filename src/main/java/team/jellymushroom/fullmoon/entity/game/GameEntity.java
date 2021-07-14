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
   * 玩家自身
   */
  private PlayerEntity mySelf = new PlayerEntity();

  /**
   * 对手玩家
   */
  private PlayerEntity opponent;

  /**
   * 历史小局游戏胜负情况
   * true: 自身胜利
   * false: 对手胜利
   */
  private List<Boolean> historyList = new ArrayList<>();
}
