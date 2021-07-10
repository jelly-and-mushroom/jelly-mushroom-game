package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameRoleEnum;
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

  public GameRoleEnum getCurrentRole() {
    return this.roleChooseService.getCurrentRole();
  }

  public boolean showRoleChooseDetal() {
    return this.roleChooseService.getShowDetail();
  }
}
