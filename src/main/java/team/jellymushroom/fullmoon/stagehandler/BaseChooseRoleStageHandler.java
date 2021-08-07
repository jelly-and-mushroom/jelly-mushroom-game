package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.SignalEntity;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

import java.util.Map;

public abstract class BaseChooseRoleStageHandler extends StageHandler {

  BaseChooseRoleStageHandler(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, Boolean fromLocal) {
    super(mainService, resourceService, httpTransferService, fromLocal);
  }

  @Override
  boolean left() {
    this.updateSignalRole(-1);
    return true;
  }

  @Override
  boolean right() {
    this.updateSignalRole(1);
    return true;
  }

  @Override
  boolean up() {
    return false;
  }

  @Override
  boolean down() {
    return false;
  }

  void updateSignalRole(int delta) {
    // 信号
    SignalEntity signal = super.activePlayer.getSignal();
    // 所有可选职业
    Map<Integer, GameRoleEntity> gameRoleMap = super.resourceService.getServiceResourceEntity().getGameRoleMap();
    // 变更后的角色值
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
