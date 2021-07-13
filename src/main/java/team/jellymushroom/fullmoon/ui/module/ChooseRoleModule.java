package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.ui.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.util.Map;

/**
 * 角色选择模块
 */
public class ChooseRoleModule extends Module {

  public ChooseRoleModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    int moduleRoleOWidth = this.iWidth / 9;
    int moduleRoleOHeight = this.iHeight / 2;
    int adjustX = 1;
    int addX = adjustX;
    Map<Integer, GameRoleEntity> gameRoleMap = this.uiService.getGameRoleMap();
    // 对手选职业
    for (Map.Entry<Integer, GameRoleEntity> roleEntry : gameRoleMap.entrySet()) {
      new RoleModule(this.uiService, this.resource, this.iX + addX, this.iY, moduleRoleOWidth, moduleRoleOHeight, 0, roleEntry.getValue(), false, false, false).draw(g);
      addX += moduleRoleOWidth;
    }
    addX = adjustX;
    // 自身选职业
    GameRoleEntity currentRole = null;
    GameRoleEntity confirmedRole = this.uiService.getGame().getMySelf().getGameRoleEntity();
    Integer confirmedRoleIndex = null==confirmedRole ? null : confirmedRole.getIndex();
    for (Map.Entry<Integer, GameRoleEntity> roleEntry : gameRoleMap.entrySet()) {
      boolean lightImg = !this.uiService.showRoleChooseDetal() && roleEntry.getKey().equals(this.uiService.getCurrentRole().getIndex());
      new RoleModule(this.uiService, this.resource, this.iX + addX, this.iY + moduleRoleOHeight, moduleRoleOWidth, moduleRoleOHeight, 0, roleEntry.getValue(), lightImg, false, roleEntry.getKey().equals(confirmedRoleIndex)).draw(g);
      addX += moduleRoleOWidth;
      if (roleEntry.getKey().equals(this.uiService.getCurrentRole().getIndex())) {
        currentRole = roleEntry.getValue();
      }
    }
    // 详细信息介绍
    if (this.uiService.showRoleChooseDetal()) {
      new RoleModule(this.uiService, this.resource, 324, 108, 376, 580, 0, currentRole, true, true, confirmedRole != null).draw(g);
    }
    super.drawWindow(g);
  }
}
