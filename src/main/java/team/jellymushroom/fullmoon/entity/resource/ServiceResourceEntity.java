package team.jellymushroom.fullmoon.entity.resource;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.GameBlessingEntity;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.state.GameStateEntity;

import java.util.*;

/**
 * service所需的静态资源(卡牌，职业，状态，效果，祝福等)
 */
@Data
public class ServiceResourceEntity {

  /**
   * key: gameRoleEntity.index
   */
  private Map<Integer, GameRoleEntity> gameRoleMap = new LinkedHashMap<>();

  /**
   * key: cardEntity.index
   */
  private Map<Integer, CardEntity> cardMap = new HashMap<>();

  private List<CardEntity> cardList = new ArrayList<>();

  /**
   * key: gameBlessingEntity.index
   */
  private Map<Integer, GameBlessingEntity> gameBlessingMap = new HashMap<>();

  /**
   * key: gameStateEntity.index
   */
  private Map<Integer, GameStateEntity> gameStateMap = new HashMap<>();
}
