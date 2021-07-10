package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameRoleEnum;
import team.jellymushroom.fullmoon.entity.ui.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

/**
 * 角色选择模块
 *
 * 初始化inner信息:
 * iWidth:1010,iHeight:725
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
    // 对手选职业
    for (GameRoleEnum gameRoleEnum : GameRoleEnum.values()) {
      new RoleModule(this.uiService, this.resource, this.iX + addX, this.iY, moduleRoleOWidth, moduleRoleOHeight, 0, gameRoleEnum, false).draw(g);
      addX += moduleRoleOWidth;
    }
    addX = adjustX;
    // 自身选职业
    for (GameRoleEnum gameRoleEnum : GameRoleEnum.values()) {
      new RoleModule(this.uiService, this.resource, this.iX + addX, this.iY + moduleRoleOHeight, moduleRoleOWidth, moduleRoleOHeight, 0, gameRoleEnum, true).draw(g);
      addX += moduleRoleOWidth;
    }
    super.drawWindow(g);
  }
}
