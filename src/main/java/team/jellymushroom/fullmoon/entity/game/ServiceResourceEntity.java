package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 游戏服务端所需的资源(卡片，职业，状态，效果，祝福等)
 */
@Data
public class ServiceResourceEntity {

  private Map<Integer, GameRoleEntity> gameRoleMap = new LinkedHashMap<>();
}
