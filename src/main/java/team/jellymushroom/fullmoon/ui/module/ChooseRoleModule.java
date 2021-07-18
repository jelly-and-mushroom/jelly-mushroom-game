package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.util.Map;

public class ChooseRoleModule extends Module {

  public ChooseRoleModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    // 获取职业map
    Map<Integer, GameRoleEntity> gameRoleMap = this.uiService.getResourceService().getServiceResourceEntity().getGameRoleMap();
    // 位置字段
    int moduleRoleOWidth = this.iWidth / gameRoleMap.size();
    int moduleRoleOHeight = this.iHeight / 2;
    int adjustX = 1;
    int addX = adjustX;
    // 对手选职业
    for (Map.Entry<Integer, GameRoleEntity> roleEntry : gameRoleMap.entrySet()) {
      boolean opponentLight = roleEntry.getValue().getIndex().equals(ServerControlEntity.getInstance().getOpponentCurrentChooseRole().getIndex());
      boolean opponentConfirm = opponentLight && null != this.uiService.getMainService().getPlayerOpponent().getGameRoleEntity();
      new ChoosePartRoleModule(this.uiService, this.resource, this.iX + addX, this.iY, moduleRoleOWidth, moduleRoleOHeight, 0, roleEntry.getValue(), opponentLight, opponentConfirm).draw(g);
      addX += moduleRoleOWidth;
    }
    addX = adjustX;
    // 自身选职业
    boolean showRoleChooseDetal = GameStageEnum.CHOOSE_ROLE_DETAIL.equals(this.uiService.getMainService().getPlayerMyself().getStage());
    GameRoleEntity currentRole = ServerControlEntity.getInstance().getCurrentChooseRole();
    for (Map.Entry<Integer, GameRoleEntity> roleEntry : gameRoleMap.entrySet()) {
      boolean light = !showRoleChooseDetal && roleEntry.getKey().equals(currentRole.getIndex());
      boolean confirm = light && null != this.uiService.getMainService().getPlayerMyself().getGameRoleEntity();
      new ChoosePartRoleModule(this.uiService, this.resource, this.iX + addX, this.iY + moduleRoleOHeight, moduleRoleOWidth, moduleRoleOHeight, 0, roleEntry.getValue(), light, confirm).draw(g);
      addX += moduleRoleOWidth;
    }
    // 详细信息介绍
    if (showRoleChooseDetal) {
      new ChooseDetailRoleModule(this.uiService, this.resource, 324, 108, 376, 580, 0, currentRole).draw(g);
    }
    // 绘制边框
    super.drawWindow(g);
  }
}
