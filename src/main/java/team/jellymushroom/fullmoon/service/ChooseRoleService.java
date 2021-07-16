package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
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
    // 所有可选职业
    Map<Integer, GameRoleEntity> gameRoleMap = this.resourceService.getServiceResourceEntity().getGameRoleMap();
    // 变更后的角色值
    int preResult = ServerControlEntity.getInstance().getCurrentChooseRole().getIndex() + delta;
    // 小于下限则设为上限
    if (preResult < 0) {
      ServerControlEntity.getInstance().setCurrentChooseRole(gameRoleMap.get(gameRoleMap.size() - 1));
      return;
    }
    // 大于上限则设为下限
    if (preResult >= gameRoleMap.size()) {
      ServerControlEntity.getInstance().setCurrentChooseRole(gameRoleMap.get(0));
      return;
    }
    // 设置为变更后的值
    ServerControlEntity.getInstance().setCurrentChooseRole(gameRoleMap.get(preResult));
  }

  public void confirm() {
    this.mainService.getPlayerMyself().setGameRoleEntity(ServerControlEntity.getInstance().getCurrentChooseRole());
  }
}
