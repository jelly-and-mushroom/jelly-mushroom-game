package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.GameEntity;

@Data
public class HttpDataEntity {

  private HttpServerControlEntity serverControl;

  private GameEntity game;
}
