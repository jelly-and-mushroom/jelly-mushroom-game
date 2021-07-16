package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;

/**
 * 服务端发给客户端，用于更新客户端的 serverControlEntity
 * 因此，本类字段也一一对应于 serverControlEntity
 */
@Data
public class HttpServerControlEntity {

  private GameRoleEntity currentChooseRole;

  private GameRoleEntity opponentCurrentChooseRole;
}
