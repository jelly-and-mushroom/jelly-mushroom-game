package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

import java.util.List;

/**
 * 服务端发给客户端，用于更新客户端的 gameEntity
 * 因此，本类字段也一一对应于 gameEntity
 */
@Data
public class HttpGameEntity {

  private HttpPlayerEntity serverPlayer;

  private HttpPlayerEntity clientPlayer;

  private List<Integer> historyIndexList;

  private Integer serverTune;

  private HttpCardEntity effectCard;
}
