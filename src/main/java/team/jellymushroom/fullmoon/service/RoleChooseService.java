package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;

import java.util.Map;

@Service
public class RoleChooseService {

  private MainService mainService;

  private ResourceService resourceService;

  @Getter
  private GameRoleEntity currentRole;

  public RoleChooseService(MainService mainService, ResourceService resourceService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
    this.currentRole = this.resourceService.getServiceResourceEntity().getGameRoleMap().get(0);
  }

  @Getter
  @Setter
  private Boolean showDetail = false;

  public void updateRole(int delta) {
    if (null != this.mainService.getGameEntity().getMySelf().getGameRoleEntity()) {
      return;
    }
    Map<Integer, GameRoleEntity> gameRoleMap = this.resourceService.getServiceResourceEntity().getGameRoleMap();
    int preResult = this.currentRole.getIndex() + delta;
    if (preResult < 0) {
      this.currentRole = gameRoleMap.get(gameRoleMap.size() - 1);
      return;
    }
    if (preResult >= gameRoleMap.size()) {
      this.currentRole  = gameRoleMap.get(0);
      return;
    }
    this.currentRole  = gameRoleMap.get(preResult);
  }

  public void confirm() {
    this.mainService.getGameEntity().getMySelf().setGameRoleEntity(currentRole);
  }
}
