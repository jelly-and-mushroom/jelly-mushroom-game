package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;

import java.util.Map;

@Service
public class UIService {

  @Getter
  private MainService mainService;

  private RoleChooseService roleChooseService;

  private ResourceService resourceService;

  public UIService(MainService mainService, RoleChooseService roleChooseService, ResourceService resourceService) {
    this.mainService = mainService;
    this.roleChooseService = roleChooseService;
    this.resourceService = resourceService;
  }

  public String getResourceRootPath() {
    return this.resourceService.getResourceRootPath();
  }

  public GameEntity getGame() {
    return this.mainService.getGameEntity();
  }

  public GameRoleEntity getCurrentRole() {
    return this.roleChooseService.getCurrentRole();
  }

  public boolean showRoleChooseDetal() {
    return GameStageEnum.CHOOSE_ROLE_DETAIL.equals(this.mainService.getPlayerMyself().getStage());
  }

  public Map<Integer, GameRoleEntity> getGameRoleMap() {
    return this.resourceService.getServiceResourceEntity().getGameRoleMap();
  }
}
