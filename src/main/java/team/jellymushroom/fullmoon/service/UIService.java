package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.GameEntity;

@Service
public class UIService {

  private MainService mainService;

  private RoleChooseService roleChooseService;

  public UIService(MainService mainService, RoleChooseService roleChooseService) {
    this.mainService = mainService;
    this.roleChooseService = roleChooseService;
  }

  public String getResourceRootPath() {
    return this.mainService.getResourceRootPath();
  }

  public GameEntity getGame() {
    return this.mainService.getGameEntity();
  }

  public Integer getCurrentRoleIndex() {
    return this.roleChooseService.getCurrentRoleIndex();
  }
}
