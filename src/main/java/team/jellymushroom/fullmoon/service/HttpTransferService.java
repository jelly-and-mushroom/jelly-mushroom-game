package team.jellymushroom.fullmoon.service;

import lombok.Getter;
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
import team.jellymushroom.fullmoon.entity.http.HttpGameEntity;
import team.jellymushroom.fullmoon.entity.http.HttpGameInnerEntity;
import team.jellymushroom.fullmoon.entity.http.HttpPlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HttpTransferService {

  private ResourceService resourceService;

  @Value("${fm.http.opponent.host}")
  @Getter
  private String httpOpponentHost;

  public static final Long HTTP_RETRY_INTERVAL = 1000L;

  public HttpTransferService(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  public HttpGameEntity convert(GameEntity game) {
    // 判空
    if (null == game) {
      return null;
    }
    // 返回值
    HttpGameEntity httpGame = new HttpGameEntity();
    // 服务端玩家
    if (null != game.getServerPlayer()) {
      httpGame.setServerPlayer(this.convert(game.getServerPlayer()));
    }
    // 客户端玩家
    if (null != game.getClientPlayer()) {
      httpGame.setClientPlayer(this.convert(game.getClientPlayer()));
    }
    // 历史胜负
    if (!game.getHistoryList().isEmpty()) {
      List<Integer> historyIndexList = new ArrayList<>(game.getHistoryList().size());
      game.getHistoryList().forEach(e -> historyIndexList.add(e.getIndex()));
      httpGame.setHistoryIndexList(historyIndexList);
    }
    // 回合所属人
    if (null != game.getServerTune()) {
      httpGame.setServerTune(game.getServerTune() ? 1 : 0);
    }
    // 当前场上生效卡牌
    if (null != game.getEffectCard()) {
      httpGame.setEffectCardIndex(game.getEffectCard().getIndex());
    }
    // 返回
    return httpGame;
  }

  public GameEntity convert(HttpGameEntity httpGame) {
    // 判空
    if (null == httpGame) {
      return null;
    }
    // 返回值
    GameEntity game = new GameEntity();
    // 服务端玩家
    if (null != httpGame.getServerPlayer()) {
      game.setServerPlayer(this.convert(httpGame.getServerPlayer()));
    }
    // 客户端玩家
    if (null != httpGame.getClientPlayer()) {
      game.setClientPlayer(this.convert(httpGame.getClientPlayer()));
    }
    // 历史胜负
    if (null != httpGame.getHistoryIndexList() && !httpGame.getHistoryIndexList().isEmpty()) {
      httpGame.getHistoryIndexList().forEach(e -> game.getHistoryList().add(GameResultEnum.getEnumByIndex(e)));
    }
    // 回合所属人
    if (null != httpGame.getServerTune()) {
      game.setServerTune(httpGame.getServerTune()==1 ? true : false);
    }
    // 当前场上生效卡牌
    if (null != httpGame.getEffectCardIndex()) {
      game.setEffectCard(this.resourceService.getServiceResourceEntity().getCardMap().get(httpGame.getEffectCardIndex()));
    }
    // 返回
    return game;
  }

  private HttpPlayerEntity convert(PlayerEntity player) {
    // 返回值
    HttpPlayerEntity httpPlayer = new HttpPlayerEntity();
    // 玩家所处阶段
    if (null != player.getStage()) {
      httpPlayer.setStageIndex(player.getStage().getIndex());
    }
    // 所选职业
    if (null != player.getGameRoleEntity()) {
      httpPlayer.setGameRoleIndex(player.getGameRoleEntity().getIndex());
    }
    // 当前回合
    if (null != player.getGameInnerEntity()) {
      httpPlayer.setGameInnerEntity(this.convert(player.getGameInnerEntity()));
    }
    // 生命值上限
    if (null != player.getMaxHp()) {
      httpPlayer.setMaxHp(player.getMaxHp());
    }
    // 初始魔法值
    if (null != player.getInitMp()) {
      httpPlayer.setInitMp(player.getInitMp());
    }
    // 行动力上限
    if (null != player.getMaxAction()) {
      httpPlayer.setMaxAction(player.getMaxAction());
    }
    // 小局游戏刚开始时的手牌数
    if (null != player.getInitHandCardSize()) {
      httpPlayer.setInitHandCardSize(player.getInitHandCardSize());
    }
    // 抽牌数
    if (null != player.getDrawCardSize()) {
      httpPlayer.setDrawCardSize(player.getDrawCardSize());
    }
    // 每回合结束时的手牌上限
    if (null != player.getMaxHandCardSize()) {
      httpPlayer.setMaxHandCardSize(player.getMaxHandCardSize());
    }
    // 本局游戏持有卡牌列表
    if (!player.getCardList().isEmpty()) {
      List<Integer> cardIndexList = new ArrayList<>(player.getCardList().size());
      player.getCardList().forEach(e -> cardIndexList.add(e.getIndex()));
      httpPlayer.setCardIndexList(cardIndexList);
    }
    // 每小局游戏初始化时，装备槽数
    if (null != player.getInitEquipmentSlotSize()) {
      httpPlayer.setInitEquipmentSlotSize(player.getInitEquipmentSlotSize());
    }
    // 当前获得的祝福
    if (!player.getGameBlessingList().isEmpty()) {
      List<Integer> gameBlessingIndexList = new ArrayList<>(player.getGameBlessingList().size());
      player.getGameBlessingList().forEach(e -> gameBlessingIndexList.add(e.getIndex()));
      httpPlayer.setGameBlessingIndexList(gameBlessingIndexList);
    }
    // 持有的金币数
    if (null != player.getGold()) {
      httpPlayer.setGold(player.getGold());
    }
    // 返回
    return httpPlayer;
  }

  private PlayerEntity convert(HttpPlayerEntity httpPlayer) {
    // 返回值
    PlayerEntity player = new PlayerEntity();
    // 玩家所处阶段
    if (null != httpPlayer.getStageIndex()) {
      player.setStage(GameStageEnum.getEnumByIndex(httpPlayer.getStageIndex()));
    }
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
    if (null != httpPlayer.getMaxHp()) {
      player.setMaxHp(httpPlayer.getMaxHp());
    }
    // 初始魔法值
    if (null != httpPlayer.getInitMp()) {
      player.setInitMp(httpPlayer.getInitMp());
    }
    // 行动力上限
    if (null != httpPlayer.getMaxAction()) {
      player.setMaxAction(httpPlayer.getMaxAction());
    }
    // 小局游戏刚开始时的手牌数
    if (null != httpPlayer.getInitHandCardSize()) {
      player.setInitHandCardSize(httpPlayer.getInitHandCardSize());
    }
    // 抽牌数
    if (null != httpPlayer.getDrawCardSize()) {
      player.setDrawCardSize(httpPlayer.getDrawCardSize());
    }
    // 每回合结束时的手牌上限
    if (null != httpPlayer.getMaxHandCardSize()) {
      player.setMaxHandCardSize(httpPlayer.getMaxHandCardSize());
    }
    // 本局游戏持有卡牌列表
    Map<Integer, CardEntity> cardMap = this.resourceService.getServiceResourceEntity().getCardMap();
    if (null != httpPlayer.getCardIndexList() && !httpPlayer.getCardIndexList().isEmpty()) {
      httpPlayer.getCardIndexList().forEach(e -> player.getCardList().add(cardMap.get(e)));
    }
    // 每小局游戏初始化时，装备槽数
    if (null != httpPlayer.getInitEquipmentSlotSize()) {
      player.setInitEquipmentSlotSize(httpPlayer.getInitEquipmentSlotSize());
    }
    // 当前获得的祝福
    Map<Integer, GameBlessingEntity> blessingMap = this.resourceService.getServiceResourceEntity().getGameBlessingMap();
    if (null != httpPlayer.getGameBlessingIndexList() && !httpPlayer.getGameBlessingIndexList().isEmpty()) {
      httpPlayer.getGameBlessingIndexList().forEach(e -> player.getGameBlessingList().add(blessingMap.get(e)));
    }
    // 持有的金币数
    if (null != httpPlayer.getGold()) {
      player.setGold(httpPlayer.getGold());
    }
    // 返回
    return player;
  }

  private HttpGameInnerEntity convert(GameInnerEntity gameInner) {
    // 返回值
    HttpGameInnerEntity httpGameInner = new HttpGameInnerEntity();
    // 本小局游戏当前生命值上限
    if (null != gameInner.getMaxHp()) {
      httpGameInner.setMaxHp(gameInner.getMaxHp());
    }
    // 本小局当前生命值
    if (null != gameInner.getHp()) {
      httpGameInner.setHp(gameInner.getHp());
    }
    // 本小局当前魔法值
    if (null != gameInner.getMp()) {
      httpGameInner.setMp(gameInner.getMp());
    }
    // 本小局游戏当前行动力上限
    if (null != gameInner.getMaxAction()) {
      httpGameInner.setMaxAction(gameInner.getMaxAction());
    }
    // 本小局当前行动力
    if (null != gameInner.getAction()) {
      httpGameInner.setAction(gameInner.getAction());
    }
    // 抽牌数
    if (null != gameInner.getDrawCardSize()) {
      httpGameInner.setDrawCardSize(gameInner.getDrawCardSize());
    }
    // 当前小局，每回合结束时的手牌上限
    if (null != gameInner.getMaxHandCardSize()) {
      httpGameInner.setMaxHandCardSize(gameInner.getMaxHandCardSize());
    }
    // 本小局游戏当前手牌列表
    if (!gameInner.getHandCardList().isEmpty()) {
      List<Integer> handCardIndexList = new ArrayList<>(gameInner.getHandCardList().size());
      gameInner.getHandCardList().forEach(e -> handCardIndexList.add(e.getIndex()));
      httpGameInner.setHandCardIndexList(handCardIndexList);
    }
    // 本小局游戏当前牌堆列表
    if (!gameInner.getHeapCardList().isEmpty()) {
      List<Integer> heapCardIndexList = new ArrayList<>(gameInner.getHeapCardList().size());
      gameInner.getHeapCardList().forEach(e -> heapCardIndexList.add(e.getIndex()));
      httpGameInner.setHeapCardIndexList(heapCardIndexList);
    }
    // 本小局游戏当前坟场列表
    if (!gameInner.getTombCardList().isEmpty()) {
      List<Integer> tombCardIndexList = new ArrayList<>(gameInner.getTombCardList().size());
      gameInner.getTombCardList().forEach(e -> tombCardIndexList.add(e.getIndex()));
      httpGameInner.setTombCardIndexList(tombCardIndexList);
    }
    // 本小局当前移除卡牌列表
    if (!gameInner.getRemoveCardList().isEmpty()) {
      List<Integer> removeCardIndexList = new ArrayList<>(gameInner.getRemoveCardList().size());
      gameInner.getRemoveCardList().forEach(e -> removeCardIndexList.add(e.getIndex()));
      httpGameInner.setRemoveCardIndexList(removeCardIndexList);
    }
    // 当前已装备的装备牌列表
    if (!gameInner.getEquipmentCardPlaceList().isEmpty()) {
      List<Integer> equipmentCardPlaceIndexList = new ArrayList<>(gameInner.getEquipmentCardPlaceList().size());
      gameInner.getEquipmentCardPlaceList().forEach(e -> equipmentCardPlaceIndexList.add(e.getIndex()));
      httpGameInner.setEquipmentCardPlaceIndexList(equipmentCardPlaceIndexList);
    }
    // 当前已放置的反制牌列表
    if (!gameInner.getCounterCardPlaceList().isEmpty()) {
      List<Integer> counterCardPlaceIndexList = new ArrayList<>(gameInner.getCounterCardPlaceList().size());
      gameInner.getCounterCardPlaceList().forEach(e -> counterCardPlaceIndexList.add(e.getIndex()));
      httpGameInner.setCounterCardPlaceIndexList(counterCardPlaceIndexList);
    }
    // 当前已放置的祷告牌列表
    if (!gameInner.getPrayerCardPlaceList().isEmpty()) {
      List<Integer> prayerCardPlaceIndexList = new ArrayList<>(gameInner.getPrayerCardPlaceList().size());
      gameInner.getPrayerCardPlaceList().forEach(e -> prayerCardPlaceIndexList.add(e.getIndex()));
      httpGameInner.setPrayerCardPlaceIndexList(prayerCardPlaceIndexList);
    }
    // 本小局游戏中，当前获得的祝福
    if (!gameInner.getBlessingList().isEmpty()) {
      List<Integer> blessingIndexList = new ArrayList<>(gameInner.getBlessingList().size());
      gameInner.getBlessingList().forEach(e -> blessingIndexList.add(e.getIndex()));
      httpGameInner.setBlessingIndexList(blessingIndexList);
    }
    // 本小局游戏中，当前获得的状态
    if (!gameInner.getGameStateList().isEmpty()) {
      List<Integer> gameStateIndexList = new ArrayList<>(gameInner.getGameStateList().size());
      gameInner.getGameStateList().forEach(e -> gameStateIndexList.add(e.getIndex()));
      httpGameInner.setGameStateIndexList(gameStateIndexList);
    }
    // 返回
    return httpGameInner;
  }

  private GameInnerEntity convert(HttpGameInnerEntity httpGameInner) {
    // 返回值
    GameInnerEntity gameInner = new GameInnerEntity();
    // 本小局游戏当前生命值上限
    if (null != httpGameInner.getMaxHp()) {
      gameInner.setMaxHp(httpGameInner.getMaxHp());
    }
    // 本小局当前生命值
    if (null != httpGameInner.getHp()) {
      gameInner.setHp(httpGameInner.getHp());
    }
    // 本小局当前魔法值
    if (null != httpGameInner.getMp()) {
      gameInner.setMp(httpGameInner.getMp());
    }
    // 本小局游戏当前行动力上限
    if (null != httpGameInner.getMaxAction()) {
      gameInner.setMaxAction(httpGameInner.getMaxAction());
    }
    // 本小局当前行动力
    if (null != httpGameInner.getAction()) {
      gameInner.setAction(httpGameInner.getAction());
    }
    // 抽牌数
    if (null != httpGameInner.getDrawCardSize()) {
      gameInner.setDrawCardSize(httpGameInner.getDrawCardSize());
    }
    // 当前小局，每回合结束时的手牌上限
    if (null != httpGameInner.getMaxHandCardSize()) {
      gameInner.setMaxHandCardSize(httpGameInner.getMaxHandCardSize());
    }
    // 本小局游戏当前手牌列表
    Map<Integer, CardEntity> cardMap = this.resourceService.getServiceResourceEntity().getCardMap();
    if (null != httpGameInner.getHandCardIndexList() && !httpGameInner.getHandCardIndexList().isEmpty()) {
      httpGameInner.getHandCardIndexList().forEach(e -> gameInner.getHandCardList().add(cardMap.get(e)));
    }
    // 本小局游戏当前牌堆列表
    if (null != httpGameInner.getHeapCardIndexList() && !httpGameInner.getHeapCardIndexList().isEmpty()) {
      httpGameInner.getHeapCardIndexList().forEach(e -> gameInner.getHeapCardList().add(cardMap.get(e)));
    }
    // 本小局游戏当前坟场列表
    if (null != httpGameInner.getTombCardIndexList() && !httpGameInner.getTombCardIndexList().isEmpty()) {
      httpGameInner.getTombCardIndexList().forEach(e -> gameInner.getTombCardList().add(cardMap.get(e)));
    }
    // 本小局当前移除卡牌列表
    if (null != httpGameInner.getRemoveCardIndexList() && !httpGameInner.getRemoveCardIndexList().isEmpty()) {
      httpGameInner.getRemoveCardIndexList().forEach(e -> gameInner.getRemoveCardList().add(cardMap.get(e)));
    }
    // 当前已装备的装备牌列表
    if (null != httpGameInner.getEquipmentCardPlaceIndexList() && !httpGameInner.getEquipmentCardPlaceIndexList().isEmpty()) {
      httpGameInner.getEquipmentCardPlaceIndexList().forEach(e -> gameInner.getEquipmentCardPlaceList().add((EquipmentCardEntity)cardMap.get(e)));
    }
    // 当前已放置的反制牌列表
    if (null != httpGameInner.getCounterCardPlaceIndexList() && !httpGameInner.getCounterCardPlaceIndexList().isEmpty()) {
      httpGameInner.getCounterCardPlaceIndexList().forEach(e -> gameInner.getCounterCardPlaceList().add((CounterCardEntity)cardMap.get(e)));
    }
    // 当前已放置的祷告牌列表
    if (null != httpGameInner.getPrayerCardPlaceIndexList() && !httpGameInner.getPrayerCardPlaceIndexList().isEmpty()) {
      httpGameInner.getPrayerCardPlaceIndexList().forEach(e -> gameInner.getPrayerCardPlaceList().add((PrayerCardEntity)cardMap.get(e)));
    }
    // 本小局游戏中，当前获得的祝福
    Map<Integer, GameBlessingEntity> blessingMap = this.resourceService.getServiceResourceEntity().getGameBlessingMap();
    if (null != httpGameInner.getBlessingIndexList() && !httpGameInner.getBlessingIndexList().isEmpty()) {
      httpGameInner.getBlessingIndexList().forEach(e -> gameInner.getBlessingList().add(blessingMap.get(e)));
    }
    // 本小局游戏中，当前获得的状态
    Map<Integer, GameStateEntity> gameStateMap = this.resourceService.getServiceResourceEntity().getGameStateMap();
    if (null != httpGameInner.getGameStateIndexList() && !httpGameInner.getGameStateIndexList().isEmpty()) {
      httpGameInner.getGameStateIndexList().forEach(e -> gameInner.getGameStateList().add(gameStateMap.get(e)));
    }
    // 返回
    return gameInner;
  }
}
