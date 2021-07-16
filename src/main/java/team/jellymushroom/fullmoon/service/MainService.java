package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Service
public class MainService {

  @Getter
  private GameEntity gameEntity;

  @Getter
  private ServerControlEntity serverControlEntity = new ServerControlEntity();

  public GameStageEnum getGameStage() {
    if (null == this.getGameEntity()) {
      return null;
    }
    if (this.serverControlEntity.getIsServer()) {
      return this.getGameEntity().getServerPlayer().getStage();
    }
    return this.getGameEntity().getClientPlayer().getStage();
  }

  public PlayerEntity getPlayerMyself() {
    return this.serverControlEntity.getIsServer() ? this.gameEntity.getServerPlayer() : this.gameEntity.getClientPlayer();
  }

  public PlayerEntity getPlayerOpponent() {
    return this.serverControlEntity.getIsServer() ? this.gameEntity.getClientPlayer() : this.gameEntity.getServerPlayer();
  }

  public void setGameStage(GameStageEnum stage) {
    if (this.serverControlEntity.getIsServer()) {
      this.gameEntity.getServerPlayer().setStage(stage);
    } else {
      this.gameEntity.getClientPlayer().setStage(stage);
    }
  }
}
