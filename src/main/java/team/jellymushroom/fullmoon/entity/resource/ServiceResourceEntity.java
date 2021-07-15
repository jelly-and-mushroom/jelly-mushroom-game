package team.jellymushroom.fullmoon.entity.resource;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * service所需的静态资源(卡牌，职业，状态，效果，祝福等)
 */
@Data
public class ServiceResourceEntity {

  /**
   * key: gameRoleEntity.index
   */
  private Map<Integer, GameRoleEntity> gameRoleMap = new LinkedHashMap<>();
}
