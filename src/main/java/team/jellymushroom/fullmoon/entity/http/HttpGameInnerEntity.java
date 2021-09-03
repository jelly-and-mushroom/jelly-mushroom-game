package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

import java.util.ArrayList;
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

  private List<HttpCardEntity> handCardList = new ArrayList<>();

  private List<HttpCardEntity> heapCardList = new ArrayList<>();

  private List<HttpCardEntity> tombCardList = new ArrayList<>();

  private List<HttpCardEntity> removeCardList = new ArrayList<>();

  private List<HttpCardEntity> equipmentCardPlaceList = new ArrayList<>();

  private List<HttpCardEntity> counterCardPlaceList = new ArrayList<>();

  private List<HttpCardEntity> prayerCardPlaceList = new ArrayList<>();

  private List<Integer> blessingIndexList = new ArrayList<>();
}
