package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏玩家
 */
@Data
public class PlayerEntity {

  /**
   * 所处阶段
   */
  private GameStageEnum stage = GameStageEnum.CHOOSE_ROLE;

  /**
   * 本局游戏所选职业
   * 一旦选定，则不可修改
   */
  private GameRoleEntity gameRoleEntity;

  /**
   * 只有当游戏阶段为某小局游戏进行中时本字段才有意义
   * 记录当前正在进行的小局游戏的静态信息
   */
  private GameInnerEntity gameInnerEntity;

  /**
   * 生命值上限
   *
   */
  private Integer maxHp;

  /**
   * 游戏初始时的魔法值
   */
  private Integer initMp = 0;

  /**
   * 行动力上限
   */
  private Integer maxAction = 1;

  /**
   * 每小局游戏刚开始时的手牌数
   */
  private Integer initHandCardSize = 3;

  /**
   * 抽牌数
   */
  private Integer drawCardSize = 2;

  /**
   * 每回合结束时的手牌上限
   */
  private Integer maxHandCardSize = 2;

  /**
   * 本局游戏持有卡牌列表
   * 按cardEntity.index递增排序
   */
  private List<CardEntity> cardList = new ArrayList<>();

  /**
   * 每小局游戏初始化时，装备槽数
   */
  private Integer initEquipmentSlotSize = 1;

  /**
   * 当前获得的祝福
   * 按获得顺序排序
   */
  private List<GameBlessingEntity> gameBlessingList = new ArrayList<>();

  /**
   * 持有的金币数
   */
  private Integer gold = 0;

  public void initByRole(GameRoleEntity role) {
    this.gameRoleEntity = role;
    this.maxHp = this.gameRoleEntity.getInitMaxHp();
    this.initMp = this.gameRoleEntity.getInitMp();
    this.maxAction = this.gameRoleEntity.getInitMaxAction();
    this.gold = this.gameRoleEntity.getInitGold();
  }
}
