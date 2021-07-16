package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Service
public class MainService {

  @Getter
  private Boolean isServer;

  @Getter
  private GameEntity gameEntity;

  public GameStageEnum getGameStage() {
    if (null == this.getGameEntity()) {
      return null;
    }
    if (this.isServer) {
      return this.getGameEntity().getServerPlayer().getStage();
    }
    return this.getGameEntity().getClientPlayer().getStage();
  }

  public PlayerEntity getPlayerMyself() {
    return this.isServer ? this.gameEntity.getServerPlayer() : this.gameEntity.getClientPlayer();
  }

  public PlayerEntity getPlayerOpponent() {
    return this.isServer ? this.gameEntity.getClientPlayer() : this.gameEntity.getServerPlayer();
  }

  public void setGameStage(GameStageEnum stage) {
    if (this.isServer) {
      this.gameEntity.getServerPlayer().setStage(stage);
    } else {
      this.gameEntity.getClientPlayer().setStage(stage);
    }
  }
}
