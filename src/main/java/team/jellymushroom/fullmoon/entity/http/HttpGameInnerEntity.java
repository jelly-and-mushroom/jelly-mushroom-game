package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

import java.util.List;

/**
 * 服务端发给客户端，用于更新客户端的 gameInnerEntity
 * 因此，本类字段也一一对应于 gameInnerEntity
 */
@Data
public class HttpGameInnerEntity {

  private Integer maxHp;

  private Integer hp;

  private Integer mp;

  private Integer maxAction;

  private Integer action;

  private Integer drawCardSize;

  private Integer maxHandCardSize;

  private List<HttpCardEntity> handCardList;

  private List<HttpCardEntity> heapCardList;

  private List<HttpCardEntity> tombCardList;

  private List<HttpCardEntity> removeCardList;

  private List<HttpCardEntity> equipmentCardPlaceList;

  private List<HttpCardEntity> counterCardPlaceList;

  private List<HttpCardEntity> prayerCardPlaceList;

  private List<Integer> blessingIndexList;

  private List<Integer> gameStateIndexList;
}
