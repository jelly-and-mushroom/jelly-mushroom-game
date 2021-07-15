package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.state.GameStateEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏可选职业
 */
@Data
public class GameRoleEntity {

  /**
   * 从0开始，职业编号
   */
  private Integer index;

  /**
   * 职业英文名
   */
  private String nameEng;

  /**
   * 职业中文名
   */
  private String nameCn;

  /**
   * 初始生命值上限
   */
  private Integer initMaxHp;

  /**
   * 初始魔法值
   */
  private Integer initMp;

  /**
   * 初始行动力上限
   */
  private Integer initMaxAction;

  /**
   * 初始持有的金币数
   */
  private Integer initGold;

  /**
   * 每小局游戏结束后，固定可获得的金币数
   */
  private Integer goldBasicAdd;

  /**
   * 若小局游戏获胜，额外可获得的金币奖励
   */
  private Integer goldWinAdd;

  /**
   * 因角色特性，每小局游戏开始时，固定可获得的状态(例如狼人的狂怒)
   */
  private List<GameStateEntity> gameStateList = new ArrayList<>();
}
