package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
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
    boolean opponentConfirm = null != this.uiService.getMainService().getPlayerOpponent().getGameRoleEntity();
    for (Map.Entry<Integer, GameRoleEntity> roleEntry : gameRoleMap.entrySet()) {
      boolean opponentLight = roleEntry.getValue().equals(this.uiService.getMainService().getServerControlEntity().getOpponentCurrentChooseRole());
      new RoleModule(this.uiService, this.resource, this.iX + addX, this.iY, moduleRoleOWidth, moduleRoleOHeight, 0, roleEntry.getValue(), opponentLight, false, opponentConfirm).draw(g);
      addX += moduleRoleOWidth;
    }
    addX = adjustX;
    // 自身选职业
    GameRoleEntity currentRole = null;
    GameRoleEntity confirmedRole = this.uiService.getMainService().getPlayerMyself().getGameRoleEntity();
    Integer confirmedRoleIndex = null==confirmedRole ? null : confirmedRole.getIndex();
    Integer currentRoleIndex = this.uiService.getMainService().getServerControlEntity().getCurrentChooseRole().getIndex();
    boolean showRoleChooseDetal = GameStageEnum.CHOOSE_ROLE_DETAIL.equals(this.uiService.getMainService().getPlayerMyself().getStage());
    for (Map.Entry<Integer, GameRoleEntity> roleEntry : gameRoleMap.entrySet()) {
      boolean lightImg = !showRoleChooseDetal && roleEntry.getKey().equals(currentRoleIndex);
      new RoleModule(this.uiService, this.resource, this.iX + addX, this.iY + moduleRoleOHeight, moduleRoleOWidth, moduleRoleOHeight, 0, roleEntry.getValue(), lightImg, false, roleEntry.getKey().equals(confirmedRoleIndex)).draw(g);
      addX += moduleRoleOWidth;
      if (roleEntry.getKey().equals(currentRoleIndex)) {
        currentRole = roleEntry.getValue();
      }
    }
    // 详细信息介绍
    if (showRoleChooseDetal) {
      new RoleModule(this.uiService, this.resource, 324, 108, 376, 580, 0, currentRole, true, true, false).draw(g);
    }
    super.drawWindow(g);
  }
}
