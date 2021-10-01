package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务端发给客户端，用于更新客户端的 playerEntity
 * 因此，本类字段也一一对应于 playerEntity
 */
@Data
public class HttpPlayerEntity {

  private HttpSignalEntity httpSignal;

  private Integer stageIndex;

  private Integer gameRoleIndex;

  private HttpGameInnerEntity gameInnerEntity;

  private Integer maxHp;

  private Integer initMp;

  private Integer maxAction;

  private Integer initHandCardSize;

  private Integer drawCardSize;

  private Integer maxHandCardSize;

  private List<HttpCardEntity> cardList = new ArrayList<>();

  private Integer initEquipmentSlotSize;

  private List<Integer> blessingIndexList = new ArrayList<>();

  private Integer gold;

  private List<HttpEffectiveStateEntity> effectiveStateList = new ArrayList<>();

  private Integer deleteUncostTimes;

  private Double rightCardCostRate;
}
