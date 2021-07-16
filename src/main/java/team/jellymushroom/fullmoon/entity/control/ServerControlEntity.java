package team.jellymushroom.fullmoon.entity.control;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;

/**
 * 游戏各阶段用到的信号值
 * 只会在某阶段做控制使用，不会存入存档
 */
@Data
public class ServerControlEntity {

  private static ServerControlEntity INSTANCE = new ServerControlEntity();

  private ServerControlEntity() {
  }

  public static ServerControlEntity getInstance() {
    return INSTANCE;
  }

  /**
   * true-服务端 false-客户端 null-尚未确定
   */
  private Boolean isServer;

  /**
   * 游戏选择阶段，当前选择的角色
   */
  private GameRoleEntity currentChooseRole;

  /**
   * 游戏选择阶段，对手当前选择的角色
   */
  private GameRoleEntity opponentCurrentChooseRole;

  /**
   * 约等于程序启动时间
   */
  private Long initTime = System.currentTimeMillis();
}
