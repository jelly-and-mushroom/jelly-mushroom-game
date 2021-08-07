package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.SignalEntity;

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
    SignalEntity signal = this.mainService.getPlayerMyself().getSignal();
    int preResult = signal.getIndex() + delta;
    // 小于下限则设为上限
    if (preResult < 0) {
      signal.setIndex(gameRoleMap.size() - 1);
      return;
    }
    // 大于上限则设为下限
    if (preResult >= gameRoleMap.size()) {
      signal.setIndex(gameRoleMap.get(0).getIndex());
      return;
    }
    // 设置为变更后的值
    signal.setIndex(preResult);
  }

  public void updateOpponentRole(int delta) {
    // 所有可选职业
    Map<Integer, GameRoleEntity> gameRoleMap = this.resourceService.getServiceResourceEntity().getGameRoleMap();
    // 变更后的角色值
    SignalEntity signal = this.mainService.getPlayerOpponent().getSignal();
    int preResult = signal.getIndex() + delta;
    // 小于下限则设为上限
    if (preResult < 0) {
      signal.setIndex(gameRoleMap.size() - 1);
      return;
    }
    // 大于上限则设为下限
    if (preResult >= gameRoleMap.size()) {
      signal.setIndex(gameRoleMap.get(0).getIndex());
      return;
    }
    // 设置为变更后的值
    signal.setIndex(preResult);
  }
}
