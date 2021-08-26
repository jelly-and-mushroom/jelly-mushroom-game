package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameResultEnum;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.*;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.CounterCardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.entity.game.card.PrayerCardEntity;
import team.jellymushroom.fullmoon.entity.game.state.GameStateEntity;
import team.jellymushroom.fullmoon.entity.http.*;

import java.util.Map;

@Service
public class HttpTransferService {

  private ResourceService resourceService;

  @Value("${fm.http.opponent.host}")
  @Getter
  private String httpOpponentHost;

  /**
   * 为保证联机事件同步的准确性
   * 当一个要发给远端的http请求尚未收到正确响应前
   * 不接收新的指令输入
   */
  @Getter
  @Setter
  private Boolean httpSendWait = false;

  public static final Long HTTP_RETRY_INTERVAL = 1000L;

  public HttpTransferService(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  public HttpGameEntity convert(GameEntity game) {
    // 返回值
    HttpGameEntity httpGame = new HttpGameEntity();
    // 服务端玩家
    httpGame.setServerPlayer(this.convert(game.getServerPlayer()));
    // 客户端玩家
    httpGame.setClientPlayer(this.convert(game.getClientPlayer()));
    // 历史胜负
    game.getHistoryList().forEach(e -> httpGame.getHistoryIndexList().add(e.getIndex()));
    // 回合所属人
    httpGame.setServerTune(game.getServerTune());
    // 当前场上生效卡牌
    if (null != game.getEffectCard()) {
      httpGame.setEffectCard(this.convert(game.getEffectCard()));
    }
    // 返回
    return httpGame;
  }

  public GameEntity convert(HttpGameEntity httpGame) {
    // 返回值
    GameEntity game = new GameEntity();
    // 服务端玩家
    game.setServerPlayer(this.convert(httpGame.getServerPlayer()));
    // 客户端玩家
    game.setClientPlayer(this.convert(httpGame.getClientPlayer()));
    // 历史胜负
    httpGame.getHistoryIndexList().forEach(e -> game.getHistoryList().add(GameResultEnum.getEnumByIndex(e)));
    // 回合所属人
    game.setServerTune(httpGame.getServerTune());
    // 当前场上生效卡牌
    if (null != httpGame.getEffectCard()) {
      game.setEffectCard(this.convert(httpGame.getEffectCard()));
    }
    // 返回
    return game;
  }

  private HttpPlayerEntity convert(PlayerEntity player) {
    // 返回值
    HttpPlayerEntity httpPlayer = new HttpPlayerEntity();
    // 控制信号
    httpPlayer.setHttpSignal(this.convert(player.getSignal()));
    // 玩家所处阶段
    httpPlayer.setStageIndex(player.getStage().getIndex());
    // 所选职业
    if (null != player.getGameRoleEntity()) {
      httpPlayer.setGameRoleIndex(player.getGameRoleEntity().getIndex());
    }
    // 当前回合
    if (null != player.getGameInnerEntity()) {
      httpPlayer.setGameInnerEntity(this.convert(player.getGameInnerEntity()));
    }
    // 生命值上限
    httpPlayer.setMaxHp(player.getMaxHp());
    // 初始魔法值
    httpPlayer.setInitMp(player.getInitMp());
    // 行动力上限
    httpPlayer.setMaxAction(player.getMaxAction());
    // 小局游戏刚开始时的手牌数
    httpPlayer.setInitHandCardSize(player.getInitHandCardSize());
    // 抽牌数
    httpPlayer.setDrawCardSize(player.getDrawCardSize());
    // 每回合结束时的手牌上限
    httpPlayer.setMaxHandCardSize(player.getMaxHandCardSize());
    // 本局游戏持有卡牌列表
    player.getCardList().forEach(e -> httpPlayer.getCardList().add(this.convert(e)));
    // 每小局游戏初始化时，装备槽数
    httpPlayer.setInitEquipmentSlotSize(player.getInitEquipmentSlotSize());
    // 当前获得的祝福
    player.getGameBlessingList().forEach(e -> httpPlayer.getGameBlessingIndexList().add(e.getIndex()));
    // 持有的金币数
    httpPlayer.setGold(player.getGold());
    // 返回
    return httpPlayer;
  }

  private PlayerEntity convert(HttpPlayerEntity httpPlayer) {
    // 返回值
    PlayerEntity player = new PlayerEntity();
    // 控制信号
    player.setSignal(this.convert(httpPlayer.getHttpSignal()));
    // 玩家所处阶段
    player.setStage(GameStageEnum.getEnumByIndex(httpPlayer.getStageIndex()));
    // 所选职业
    Map<Integer, GameRoleEntity> gameRoleMap = this.resourceService.getServiceResourceEntity().getGameRoleMap();
    if (null != httpPlayer.getGameRoleIndex()) {
      player.setGameRoleEntity(gameRoleMap.get(httpPlayer.getGameRoleIndex()));
    }
    // 当前回合
    if (null != httpPlayer.getGameInnerEntity()) {
      player.setGameInnerEntity(this.convert(httpPlayer.getGameInnerEntity()));
    }
    // 生命值上限
    player.setMaxHp(httpPlayer.getMaxHp());
    // 初始魔法值
    player.setInitMp(httpPlayer.getInitMp());
    // 行动力上限
    player.setMaxAction(httpPlayer.getMaxAction());
    // 小局游戏刚开始时的手牌数
    player.setInitHandCardSize(httpPlayer.getInitHandCardSize());
    // 抽牌数
    player.setDrawCardSize(httpPlayer.getDrawCardSize());
    // 每回合结束时的手牌上限
    player.setMaxHandCardSize(httpPlayer.getMaxHandCardSize());
    // 本局游戏持有卡牌列表
    httpPlayer.getCardList().forEach(e -> player.getCardList().add(this.convert(e)));
    // 每小局游戏初始化时，装备槽数
    player.setInitEquipmentSlotSize(httpPlayer.getInitEquipmentSlotSize());
    // 当前获得的祝福
    Map<Integer, GameBlessingEntity> blessingMap = this.resourceService.getServiceResourceEntity().getGameBlessingMap();
    httpPlayer.getGameBlessingIndexList().forEach(e -> player.getGameBlessingList().add(blessingMap.get(e)));
    // 持有的金币数
    player.setGold(httpPlayer.getGold());
    // 返回
    return player;
  }

  public HttpSignalEntity convert(SignalEntity signal) {
    // 返回值
    HttpSignalEntity httpSignal = new HttpSignalEntity();
    // 游戏各阶段(GameStageEnum)，主控制信号索引
    httpSignal.setIndex(signal.getIndex());
    // 游戏各阶段(GameStageEnum)，主控制信号索引2
    httpSignal.setIndex2(signal.getIndex2());
    // 游戏各阶段(GameStageEnum)，卡牌列表
    signal.getCardList().forEach(e -> httpSignal.getCardIndexList().add(e.getIndex()));
    // 游戏各阶段(GameStageEnum)，卡牌列表2
    signal.getCardList2().forEach(e -> httpSignal.getCardIndexList2().add(e.getIndex()));
    // 返回
    return httpSignal;
  }

  public SignalEntity convert(HttpSignalEntity httpSignal) {
    // 资源
    Map<Integer, CardEntity> cardMap = this.resourceService.getServiceResourceEntity().getCardMap();
    // 返回值
    SignalEntity signal = new SignalEntity();
    // 游戏各阶段(GameStageEnum)，主控制信号索引
    signal.setIndex(httpSignal.getIndex());
    // 游戏各阶段(GameStageEnum)，主控制信号索引2
    signal.setIndex2(httpSignal.getIndex2());
    // 游戏各阶段(GameStageEnum)，卡牌列表
    httpSignal.getCardIndexList().forEach(e -> signal.getCardList().add(cardMap.get(e).copy()));
    // 游戏各阶段(GameStageEnum)，卡牌列表2
    httpSignal.getCardIndexList2().forEach(e -> signal.getCardList2().add(cardMap.get(e).copy()));
    // 返回
    return signal;
  }

  private HttpGameInnerEntity convert(GameInnerEntity gameInner) {
    // 返回值
    HttpGameInnerEntity httpGameInner = new HttpGameInnerEntity();
    // 本小局游戏当前生命值上限
    httpGameInner.setMaxHp(gameInner.getMaxHp());
    // 本小局当前生命值
    httpGameInner.setHp(gameInner.getHp());
    // 本小局当前魔法值
    httpGameInner.setMp(gameInner.getMp());
    // 本小局游戏当前行动力上限
    httpGameInner.setMaxAction(gameInner.getMaxAction());
    // 本小局当前行动力
    httpGameInner.setAction(gameInner.getAction());
    // 抽牌数
    httpGameInner.setDrawCardSize(gameInner.getDrawCardSize());
    // 当前小局，每回合结束时的手牌上限
    httpGameInner.setMaxHandCardSize(gameInner.getMaxHandCardSize());
    // 本小局游戏当前手牌列表
    gameInner.getHandCardList().forEach(e -> httpGameInner.getHandCardList().add(this.convert(e)));
    // 本小局游戏当前牌堆列表
    gameInner.getHeapCardList().forEach(e -> httpGameInner.getHeapCardList().add(this.convert(e)));
    // 本小局游戏当前坟场列表
    gameInner.getTombCardList().forEach(e -> httpGameInner.getTombCardList().add(this.convert(e)));
    // 本小局当前移除卡牌列表
    gameInner.getRemoveCardList().forEach(e -> httpGameInner.getRemoveCardList().add(this.convert(e)));
    // 当前已装备的装备牌列表
    gameInner.getEquipmentCardPlaceList().forEach(e -> httpGameInner.getEquipmentCardPlaceList().add(this.convert(e)));
    // 当前已放置的反制牌列表
    gameInner.getCounterCardPlaceList().forEach(e -> httpGameInner.getCounterCardPlaceList().add(this.convert(e)));
    // 当前已放置的祷告牌列表
    gameInner.getPrayerCardPlaceList().forEach(e -> httpGameInner.getPrayerCardPlaceList().add(this.convert(e)));
    // 本小局游戏中，当前获得的祝福
    gameInner.getBlessingList().forEach(e -> httpGameInner.getBlessingIndexList().add(e.getIndex()));
    // 本小局游戏中，当前获得的状态
    gameInner.getGameStateList().forEach(e -> httpGameInner.getGameStateIndexList().add(e.getIndex()));
    // 返回
    return httpGameInner;
  }

  private GameInnerEntity convert(HttpGameInnerEntity httpGameInner) {
    // 返回值
    GameInnerEntity gameInner = new GameInnerEntity();
    // 本小局游戏当前生命值上限
    gameInner.setMaxHp(httpGameInner.getMaxHp());
    // 本小局当前生命值
    gameInner.setHp(httpGameInner.getHp());
    // 本小局当前魔法值
    gameInner.setMp(httpGameInner.getMp());
    // 本小局游戏当前行动力上限
    gameInner.setMaxAction(httpGameInner.getMaxAction());
    // 本小局当前行动力
    gameInner.setAction(httpGameInner.getAction());
    // 抽牌数
    gameInner.setDrawCardSize(httpGameInner.getDrawCardSize());
    // 当前小局，每回合结束时的手牌上限
    gameInner.setMaxHandCardSize(httpGameInner.getMaxHandCardSize());
    // 本小局游戏当前手牌列表
    httpGameInner.getHandCardList().forEach(e -> gameInner.getHandCardList().add(this.convert(e)));
    // 本小局游戏当前牌堆列表
    httpGameInner.getHeapCardList().forEach(e -> gameInner.getHeapCardList().add(this.convert(e)));
    // 本小局游戏当前坟场列表
    httpGameInner.getTombCardList().forEach(e -> gameInner.getTombCardList().add(this.convert(e)));
    // 本小局当前移除卡牌列表
    httpGameInner.getRemoveCardList().forEach(e -> gameInner.getRemoveCardList().add(this.convert(e)));
    // 当前已装备的装备牌列表
    httpGameInner.getEquipmentCardPlaceList().forEach(e -> gameInner.getEquipmentCardPlaceList().add((EquipmentCardEntity)this.convert(e)));
    // 当前已放置的反制牌列表
    httpGameInner.getCounterCardPlaceList().forEach(e -> gameInner.getCounterCardPlaceList().add((CounterCardEntity)this.convert(e)));
    // 当前已放置的祷告牌列表
    httpGameInner.getPrayerCardPlaceList().forEach(e -> gameInner.getPrayerCardPlaceList().add((PrayerCardEntity)this.convert(e)));
    // 本小局游戏中，当前获得的祝福
    Map<Integer, GameBlessingEntity> blessingMap = this.resourceService.getServiceResourceEntity().getGameBlessingMap();
    httpGameInner.getBlessingIndexList().forEach(e -> gameInner.getBlessingList().add(blessingMap.get(e)));
    // 本小局游戏中，当前获得的状态
    Map<Integer, GameStateEntity> gameStateMap = this.resourceService.getServiceResourceEntity().getGameStateMap();
    httpGameInner.getGameStateIndexList().forEach(e -> gameInner.getGameStateList().add(gameStateMap.get(e)));
    // 返回
    return gameInner;
  }

  private HttpCardEntity convert(CardEntity card) {
    HttpCardEntity httpCard = new HttpCardEntity();
    httpCard.setIndex(card.getIndex());
    httpCard.setTemp(card.getTemp());
    if (card instanceof EquipmentCardEntity) {
      EquipmentCardEntity equipmentCard = (EquipmentCardEntity)card;
      httpCard.setPlace(equipmentCard.getPlace());
    }
    return httpCard;
  }

  private CardEntity convert(HttpCardEntity httpCard) {
    CardEntity card = this.resourceService.getServiceResourceEntity().getCardMap().get(httpCard.getIndex()).copy();
    card.setTemp(httpCard.getTemp());
    if ((card instanceof EquipmentCardEntity)) {
      EquipmentCardEntity equipmentCard = (EquipmentCardEntity)card;
      equipmentCard.setPlace(httpCard.getPlace());
    }
    return card;
  }
}
