package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

@Data
public class HttpDataEntity {

  private HttpServerControlEntity serverControl;

  private HttpGameEntity game;
}
