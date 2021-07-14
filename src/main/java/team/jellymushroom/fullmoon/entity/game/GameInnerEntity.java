package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.CounterCardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.entity.game.card.PrayerCardEntity;

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
   * true: 自身的回合
   * false: 对手的回合
   */
  private Boolean myTune = false;

  /**
   * 本小局游戏当前生命上限
   * 因卡牌，祝福效果一小局游戏过程中生命上限会发生变化
   * 本小局游戏结束后，生命上限将恢复为进行本小局游戏前的状态
   */
  private Integer maxHp;

  /**
   * 本小局当前生命值
   */
  private Integer hp;

  /**
   * 本小局游戏当前行动力上限
   * 因卡牌，祝福效果一小局游戏过程中行动力上限会发生变化
   * 本小局游戏结束后，行动力上限将恢复为进行本小局游戏前的状态
   */
  private Integer maxAction;

  /**
   * 本小局当前行动力
   */
  private Integer action = maxAction;

  /**
   * 当前小局，每回合结束时的手牌上限
   */
  private Integer maxHandCardSize;

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
   * 按获得顺序排序
   */
  private List<GameBlessingEntity> blessingList = new ArrayList<>();
}
