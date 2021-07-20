package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.CounterCardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.entity.game.card.PrayerCardEntity;
import team.jellymushroom.fullmoon.entity.game.state.GameStateEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 一小局游戏
 * 只会记录当前正在进行的这一小局的信息
 * 对于一大局游戏中历史已经完成的小局，只会将胜负情况记录到所属大局游戏中
 */
@Data
public class GameInnerEntity {

  /**
   * 本小局游戏当前生命值上限
   * 因卡牌，祝福效果一小局游戏过程中生命值上限可能会发生变化
   * 因此要与全局的生命值上限做出区分
   */
  private Integer maxHp;

  /**
   * 本小局当前生命值
   */
  private Integer hp = this.maxHp;

  /**
   * 本小局当前魔法值
   */
  private Integer mp;

  /**
   * 本小局游戏当前行动力上限
   * 因卡牌，祝福效果一小局游戏过程中行动力上限可能会发生变化
   * 因此要与全局的行动力上限做出区分
   */
  private Integer maxAction;

  /**
   * 本小局当前行动力
   */
  private Integer action = this.maxAction;

  /**
   * 当前小局，每回合结束时的手牌上限
   * 因卡牌，祝福效果一小局游戏过程中手牌上限可能会发生变化
   * 因此要与全局的手牌上限做出区分
   */
  private Integer maxHandCardSize;

  /**
   * 本小局游戏当前手牌列表
   * 按抽到牌的先后排序
   */
  private List<CardEntity> handCardList = new ArrayList<>();

  /**
   * 本小局游戏当前牌堆列表
   * 排序规则同 playerEntity.cardList
   * 若在某回合未将牌堆抽尽，则下一回合不补充牌堆
   * 直到某回合将牌堆抽尽，则即便仍可抽牌，本回合也将无牌可抽
   * 到下一回合，坟场中的牌会全部消失，补充至牌堆
   */
  private List<CardEntity> heapCardList = new ArrayList<>();

  /**
   * 本小局游戏当前坟场列表
   * 按进入坟场的先后顺序排序
   */
  private List<CardEntity> tombCardList = new ArrayList<>();

  /**
   * 本小局当前移除卡牌列表
   * 按移除顺序排序
   */
  private List<CardEntity> removeCardList = new ArrayList<>();

  /**
   * 当前已装备的装备牌列表
   * 按装备顺序排序
   */
  private List<EquipmentCardEntity> equipmentCardPlaceList = new ArrayList<>();

  /**
   * 当前已放置的反制牌列表
   * 按放置顺序排序
   */
  private List<CounterCardEntity> counterCardPlaceList = new ArrayList<>();

  /**
   * 当前已放置的祷告牌列表
   * 按放置顺序排序
   */
  private List<PrayerCardEntity> prayerCardPlaceList = new ArrayList<>();

  /**
   * 本小局游戏中，当前获得的祝福
   * 因卡牌，祝福效果一小局游戏过程中祝福列表可能会发生变化
   * 因此要与全局的祝福列表做出区分
   * 按获得顺序排序
   */
  private List<GameBlessingEntity> blessingList = new ArrayList<>();

  /**
   * 本小局游戏中，当前获得的状态
   * 按获得顺序排序
   */
  private List<GameStateEntity> gameStateList = new ArrayList<>();
}
