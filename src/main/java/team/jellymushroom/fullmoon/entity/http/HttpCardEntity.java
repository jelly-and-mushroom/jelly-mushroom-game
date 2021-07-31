package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

/**
 * 服务端发给客户端，用于更新客户端的 cardEntity
 * 因此，本类字段也一一对应于 cardEntity (或子类)
 */
@Data
public class HttpCardEntity {

  private Integer index;

  private Integer temp;

  private Integer place;
}
