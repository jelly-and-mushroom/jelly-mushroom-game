package team.jellymushroom.fullmoon.stagehandler;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.SignalEntity;
import team.jellymushroom.fullmoon.service.HttpTransferService;
import team.jellymushroom.fullmoon.service.MainService;
import team.jellymushroom.fullmoon.service.ResourceService;

import java.util.Map;

/**
 * GameStageEnum.CHOOSE_ROLE
 * GameStageEnum.CHOOSE_ROLE_DETAIL
 */
public class ChooseRoleStageHandler extends StageHandler {

  public ChooseRoleStageHandler(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, Boolean fromLocal) {
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

  @Override
  boolean detail() {
    if (GameStageEnum.CHOOSE_ROLE_DETAIL.equals(super.activePlayer.getStage())) {
      return false;
    }
    super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_DETAIL);
    return true;
  }

  @Override
  boolean confirm() {
    if (GameStageEnum.CHOOSE_ROLE_DETAIL.equals(super.activePlayer.getStage())) {
      return false;
    }
    super.activePlayer.initByRole(this.resourceService);
    if (GameStageEnum.CHOOSE_ROLE_CONFIRM.equals(super.passivePlayer.getStage())) {
      super.activePlayer.getSignal().setIndex(0);
      super.passivePlayer.getSignal().setIndex(0);
      super.activePlayer.setStage(GameStageEnum.PREPARE);
      super.passivePlayer.setStage(GameStageEnum.PREPARE);
    } else {
      super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE_CONFIRM);
    }
    return true;
  }

  @Override
  boolean cancel() {
    if (GameStageEnum.CHOOSE_ROLE.equals(super.activePlayer.getStage())) {
      return false;
    }
    super.activePlayer.setStage(GameStageEnum.CHOOSE_ROLE);
    return true;
  }

  private void updateSignalRole(int delta) {
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
