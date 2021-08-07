package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.KeyEventEnum;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.runnable.HttpUpdateGameRunnable;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

public abstract class StageHandler {

  MainService mainService;

  ResourceService resourceService;

  HttpTransferService httpTransferService;

  Boolean fromLocal;

  PlayerEntity activePlayer;

  PlayerEntity passivePlayer;

  StageHandler(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, Boolean fromLocal) {
    this.mainService = mainService;
    this.resourceService = resourceService;
    this.httpTransferService = httpTransferService;
    this.fromLocal = fromLocal;
    this.activePlayer = fromLocal ? this.mainService.getGameEntity().getServerPlayer() : this.mainService.getGameEntity().getClientPlayer();
    this.passivePlayer = fromLocal ? this.mainService.getGameEntity().getClientPlayer() : this.mainService.getGameEntity().getServerPlayer();
  }

  public void handle(KeyEventEnum keyEventEnum) {
    switch (keyEventEnum) {
      case LEFT:
        this.left();
        break;
      case RIGHT:
        this.right();
        break;
      case UP:
        this.up();
        break;
      case DOWN:
        this.down();
        break;
      case DETAIL:
        this.detail();
        break;
      case CONFIRM:
        this.confirm();
        break;
      case CANCEL:
        this.cancel();
    }
  }

  void sendDataToClient() {
    new Thread(new HttpUpdateGameRunnable(this.httpTransferService, this.mainService.getGameEntity())).start();
  }

  abstract void left();

  abstract void right();

  abstract void up();

  abstract void down();

  abstract void detail();

  abstract void confirm();

  abstract void cancel();
}
