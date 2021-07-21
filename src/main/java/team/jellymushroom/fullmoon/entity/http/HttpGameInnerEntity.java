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

  private List<Integer> handCardIndexList;

  private List<Integer> heapCardIndexList;

  private List<Integer> tombCardIndexList;

  private List<Integer> removeCardIndexList;

  private List<Integer> equipmentCardPlaceIndexList;

  private List<Integer> counterCardPlaceIndexList;

  private List<Integer> prayerCardPlaceIndexList;

  private List<Integer> blessingIndexList;

  private List<Integer> gameStateIndexList;
}
