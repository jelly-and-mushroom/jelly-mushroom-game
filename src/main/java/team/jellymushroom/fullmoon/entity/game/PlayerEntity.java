package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.EffectiveStateEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.service.ResourceService;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏玩家
 */
@Data
public class PlayerEntity {

  private SignalEntity signal = new SignalEntity();

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
  private List<BlessingEntity> blessingList = new ArrayList<>();

  /**
   * 持有的金币数
   */
  private Integer gold = 0;

  /**
   * 获得的状态列表
   */
  private List<EffectiveStateEntity> effectiveStateList = new ArrayList<>();

  /**
   * 可无消耗删除卡牌的次数
   */
  private Integer deleteUncostTimes = 0;

  public void initByRole(ResourceService resourceService) {
    this.gameRoleEntity = resourceService.getServiceResourceEntity().getGameRoleMap().get(this.getSignal().getIndex());
    this.maxHp = this.gameRoleEntity.getInitMaxHp();
    this.initMp = this.gameRoleEntity.getInitMp();
    this.maxAction = this.gameRoleEntity.getInitMaxAction();
    this.gold = this.gameRoleEntity.getInitGold();
    if (!this.gameRoleEntity.getInitCardIndexList().isEmpty()) {
      this.gameRoleEntity.getInitCardIndexList().forEach(e -> this.cardList.add(resourceService.getServiceResourceEntity().getCardMap().get(e).copy()));
    }
  }

  public int getEquipmentInSoltSize() {
    int result = 0;
    List<CardEntity> cardList = this.getCardList();
    for (CardEntity card : cardList) {
      if (!(card instanceof EquipmentCardEntity)) {
        continue;
      }
      EquipmentCardEntity equipmentCard = (EquipmentCardEntity)card;
      if (equipmentCard.getPlace()) {
        result++;
      }
    }
    return result;
  }

  /**
   * 返回列表中元素的含义: this.cardList这个列表中待强化卡牌的下标索引
   */
  public List<Integer> getIntensifyCardIndexList() {
    List<Integer> result = new ArrayList<>();
    if (this.cardList.isEmpty()) {
      return result;
    }
    for (int i = 0; i < this.cardList.size(); i++) {
      if (null == this.cardList.get(i).getNextStarCardIndex()) {
        continue;
      }
      result.add(i);
    }
    return result;
  }
}
