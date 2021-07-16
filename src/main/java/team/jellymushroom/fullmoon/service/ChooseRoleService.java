package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;

import java.util.Map;

@Service
public class ChooseRoleService {

  private MainService mainService;

  private ResourceService resourceService;

  public ChooseRoleService(MainService mainService, ResourceService resourceService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
  }

  public void updateRole(int delta) {
    if (null != this.mainService.getPlayerMyself().getGameRoleEntity()) {
      return;
    }
    Map<Integer, GameRoleEntity> gameRoleMap = this.resourceService.getServiceResourceEntity().getGameRoleMap();
    GameRoleEntity currentRole = this.mainService.getServerControlEntity().getCurrentChooseRole();
    int preResult = currentRole.getIndex() + delta;
    if (preResult < 0) {
      this.mainService.getServerControlEntity().setCurrentChooseRole(gameRoleMap.get(gameRoleMap.size() - 1));
      return;
    }
    if (preResult >= gameRoleMap.size()) {
      this.mainService.getServerControlEntity().setCurrentChooseRole(gameRoleMap.get(0));
      return;
    }
    this.mainService.getServerControlEntity().setCurrentChooseRole(gameRoleMap.get(preResult));
  }

  public void confirm() {
    this.mainService.getPlayerMyself().setGameRoleEntity(this.mainService.getServerControlEntity().getCurrentChooseRole());
  }
}
