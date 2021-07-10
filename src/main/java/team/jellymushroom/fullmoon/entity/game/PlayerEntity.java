package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.GameRoleEnum;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.CounterCardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.entity.game.card.PrayerCardEntity;
import team.jellymushroom.fullmoon.entity.game.state.StateEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏玩家
 */
@Data
public class PlayerEntity {

  /**
   * 生命上限
   *
   */
  private Integer maxHp;

  /**
   * 本小局游戏当前生命上限
   * 因卡牌，祝福效果一小局游戏过程中生命上限会发生变化
   * 本小局游戏结束后，生命上限将恢复为进行本小局游戏前的状态
   */
  private Integer currentMaxHp;

  /**
   * 本小局当前生命值
   */
  private Integer currentHp;

  /**
   * 本小局刚开始时的魔法值
   */
  private Integer initMp = 0;

  /**
   * 行动力上限
   */
  private Integer maxAction = 1;

  /**
   * 本小局游戏当前行动力上限
   * 因卡牌，祝福效果一小局游戏过程中行动力上限会发生变化
   * 本小局游戏结束后，行动力上限将恢复为进行本小局游戏前的状态
   */
  private Integer currentMaxAction = maxAction;

  /**
   * 本小局当前行动力
   */
  private Integer currentAction = currentMaxAction;

  /**
   * 本小局刚开始时的手牌数
   */
  private Integer initHandCardSize = 3;

  /**
   * 每回合结束时的手牌上限
   */
  private Integer maxHandCardSize = 2;

  /**
   * 当前小局，每回合结束时的手牌上限
   */
  private Integer currentMaxHandCardSize = maxHandCardSize;

  /**
   * 本局游戏持有卡牌列表
   * 列表顺序(有固定排序规则)即为展现顺序
   */
  private List<CardEntity> cardList = new ArrayList<>();

  /**
   * 本小局游戏当前手牌列表
   * 按抽到牌的先后排序
   */
  private List<CardEntity> handCardList = new ArrayList<>();

  /**
   * 本小局游戏当前牌堆列表
   * 列表顺序(有固定排序规则，规则同cardList)即为展现顺序
   * 若在某回合未将牌堆抽尽，则下一回合不补充牌堆
   * 直到某回合将牌堆抽尽，则即便仍可抽牌，本回合也将无牌可抽
   * 到下一回合，坟场中的牌会全部消失，补充至牌堆
   */
  private List<CardEntity> heapCardList = new ArrayList<>();

  /**
   * 当前打出的，正在生效展示中的卡牌
   * 若不是自身的回合，或在自身回合中尚未打出卡牌，则本字段为null
   * 同一时刻，对两位玩家而言，最多只能有一位玩家打出一张卡牌
   */
  private CardEntity currentShowCard;

  /**
   * 本小局当前坟场列表
   * 按进入坟场的先后顺序排序
   */
  private List<CardEntity> tombCardList = new ArrayList<>();

  /**
   * 本小局当前移除卡牌列表
   * 按移除顺序排序
   */
  private List<CardEntity> removeCardList = new ArrayList<>();

  /**
   * 本局游戏所选职业
   * 一旦选定，则不可修改
   */
  private GameRoleEnum gameRoleEnum;

  /**
   * 本小局游戏初始化时，装备槽数
   */
  private Integer initEquipmentSlotSize = 1;

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
   * 当前已放置的祷告牌map
   * key: 放置的祷告牌
   * value: 距生效剩余回合数
   * 按放置顺序排序
   */
  private Map<PrayerCardEntity, Integer> prayerCardPlaceList = new LinkedHashMap<>();

  /**
   * 当前获得的状态
   * 按获得顺序排序
   */
  private List<StateEntity> stateList = new ArrayList<>();
}
