package team.jellymushroom.fullmoon.entity.resource;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.BlessingEntity;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;

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
   * key: blessingEntity.index
   */
  private Map<Integer, BlessingEntity> blessingMap = new HashMap<>();

  private List<BlessingEntity> blessingList = new ArrayList<>();
}
