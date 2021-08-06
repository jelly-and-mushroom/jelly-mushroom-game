package team.jellymushroom.fullmoon.entity.control;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;
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
   * 游戏选择阶段，当前选择的角色
   */
  private GameRoleEntity currentChooseRole;

  /**
   * 游戏选择阶段，对手当前选择的角色
   */
  private GameRoleEntity opponentCurrentChooseRole;

  /**
   * 游戏准备阶段，当前选择的选项
   */
  private PrepareOptionEnum currentPrepare;

  /**
   * 游戏准备阶段，对手当前选择的选项
   */
  private PrepareOptionEnum opponentPrepare;

  /**
   * 游戏准备阶段 - 查看卡牌列表 (我的牌库，购买卡牌，强化卡牌，删除卡牌 等)
   * 当前选中的卡牌在列表中的index
   */
  private Integer prepareCardListIndex;

  /**
   * 游戏准备阶段 - 查看卡牌列表 (我的牌库，购买卡牌，强化卡牌，删除卡牌 等)
   * 对手当前选中的卡牌在列表中的index
   */
  private Integer opponentPrepareCardListIndex;
}
