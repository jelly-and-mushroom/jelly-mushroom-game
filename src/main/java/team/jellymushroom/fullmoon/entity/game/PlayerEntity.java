package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.GameRoleEnum;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.state.StateEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏玩家
 */
@Data
public class PlayerEntity {

  /**
   * 本局游戏所选职业
   * 一旦选定，则不可修改
   */
  private GameRoleEnum gameRoleEnum;

  /**
   * 只有当前游戏阶段为 GameStageEnum.GAME_FIGHT 本字段才不会为空
   * 记录当前正在进行的小局游戏的静态信息
   */
  private GameInnerEntity gameInnerEntity;

  /**
   * 生命上限
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
   * 每回合结束时的手牌上限
   */
  private Integer maxHandCardSize = 2;

  /**
   * 本局游戏持有卡牌列表
   * 列表顺序(有固定排序规则)即为展现顺序
   */
  private List<CardEntity> cardList = new ArrayList<>();

  /**
   * 每小局游戏初始化时，装备槽数
   */
  private Integer initEquipmentSlotSize = 1;

  /**
   * 当前获得的状态
   * 按获得顺序排序
   */
  private List<StateEntity> stateList = new ArrayList<>();

  /**
   * 当前获得的祝福
   * 按获得顺序排序
   */
  private List<BlessingEntity> blessingList = new ArrayList<>();

  /**
   * 持有的金币数
   */
  private Integer gold;
}
