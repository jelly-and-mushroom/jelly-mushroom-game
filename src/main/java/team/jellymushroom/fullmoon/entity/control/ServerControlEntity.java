package team.jellymushroom.fullmoon.entity.control;

import lombok.Getter;

/**
 * 游戏各阶段用到的信号值
 * 只会在某阶段做控制使用，不会存入存档
 */
public class ServerControlEntity {

  /**
   * true-服务端 false-客户端 null-尚未确定
   */
  @Getter
  private Boolean isServer;
}
