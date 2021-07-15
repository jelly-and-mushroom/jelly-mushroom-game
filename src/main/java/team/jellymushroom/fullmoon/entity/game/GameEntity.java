package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;

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
  private PlayerEntity serverPlayer = new PlayerEntity();

  /**
   * 客户端玩家
   */
  private PlayerEntity clientPlayer = new PlayerEntity();

  /**
   * 本大局游戏已进行了多少小局游戏
   * true: serverPlayer胜利
   * false: clientPlayer胜利
   */
  private List<Boolean> historyList = new ArrayList<>();

  /**
   * 只有当游戏阶段为某小局游戏进行中时本字段才有意义
   * true: serverPlayer的回合
   * false: clientPlayer的回合
   */
  private Boolean serverTune = true;

  /**
   * 只有当游戏阶段为某小局游戏进行中时本字段才有意义
   * 当前打出的，正在生效展示中的卡牌
   * 打出人即为 this.serverTune 对应的玩家
   */
  private CardEntity effectCard;
}
