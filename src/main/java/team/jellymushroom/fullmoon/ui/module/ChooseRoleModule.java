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
      new RoleModule(this.uiService, this.resource, this.iX + addX, this.iY, moduleRoleOWidth, moduleRoleOHeight, 0, gameRoleEnum, false, false).draw(g);
      addX += moduleRoleOWidth;
    }
    addX = adjustX;
    // 自身选职业
    GameRoleEnum currentRole = null;
    for (GameRoleEnum gameRoleEnum : GameRoleEnum.values()) {
      boolean lightImg = !this.uiService.showRoleChooseDetal() && gameRoleEnum.equals(this.uiService.getCurrentRole());
      new RoleModule(this.uiService, this.resource, this.iX + addX, this.iY + moduleRoleOHeight, moduleRoleOWidth, moduleRoleOHeight, 0, gameRoleEnum, lightImg, false).draw(g);
      addX += moduleRoleOWidth;
      if (gameRoleEnum.equals(this.uiService.getCurrentRole())) {
        currentRole = gameRoleEnum;
      }
    }
    // 详细信息介绍
    if (this.uiService.showRoleChooseDetal()) {
      int detailHeight = (int)(0.8 * this.iHeight);
      int detailWidth = (int)(1.0 * detailHeight * 662 / 1022);
      new RoleModule(this.uiService, this.resource, this.iX + (this.iWidth - detailWidth) / 2, this.iY + (this.iHeight - detailHeight) / 2, detailWidth, detailHeight, 0, currentRole, true, true).draw(g);
    }
    super.drawWindow(g);
  }
}
