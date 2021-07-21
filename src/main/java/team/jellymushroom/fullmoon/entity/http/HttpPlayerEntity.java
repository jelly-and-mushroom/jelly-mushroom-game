package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

import java.util.List;

/**
 * 服务端发给客户端，用于更新客户端的 playerEntity
 * 因此，本类字段也一一对应于 playerEntity
 */
@Data
public class HttpPlayerEntity {

  private Integer stageIndex;

  private Integer gameRoleIndex;

  private HttpGameInnerEntity gameInnerEntity;

  private Integer maxHp;

  private Integer initMp;

  private Integer maxAction;

  private Integer initHandCardSize;

  private Integer drawCardSize;

  private Integer maxHandCardSize;

  private List<Integer> cardIndexList;

  private Integer initEquipmentSlotSize;

  private List<Integer> gameBlessingIndexList;

  private Integer gold;
}
