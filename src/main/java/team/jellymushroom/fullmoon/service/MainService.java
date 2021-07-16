package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Service
public class MainService {

  @Getter
  private GameEntity gameEntity;

  @Getter
  private ServerControlEntity serverControlEntity = new ServerControlEntity();

  private ResourceService resourceService;

  public MainService(ResourceService resourceService) {
    this.resourceService = resourceService;
    this.serverControlEntity.setCurrentChooseRole(this.resourceService.getServiceResourceEntity().getGameRoleMap().get(0));
  }

  public PlayerEntity getPlayerMyself() {
    return this.serverControlEntity.getIsServer() ? this.gameEntity.getServerPlayer() : this.gameEntity.getClientPlayer();
  }

  public PlayerEntity getPlayerOpponent() {
    return this.serverControlEntity.getIsServer() ? this.gameEntity.getClientPlayer() : this.gameEntity.getServerPlayer();
  }
}
