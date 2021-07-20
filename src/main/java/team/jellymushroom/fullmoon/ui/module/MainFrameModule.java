package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

public class MainFrameModule extends Module {

  public MainFrameModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    this.drawBase(g);
  }

  private void drawBase(Graphics g) {
    // 绘制背景图片
    super.drawBackgroundImg(g);
    // 角色图片尺寸 roleHeight=241
    int roleWidth = 225;
    int roleHeight = (int)(1.0 * this.iHeight / 3);
    int roleInfoGHeight = roleHeight / 2 + 2;
    // 绘制对手
    new MainFrameRoleModule(this.uiService, this.resource, this.iX, this.iY, roleWidth, roleHeight, 0, false).draw(g);
    new RoleInfoModule(this.uiService, this.resource, this.iX, this.iY + roleHeight, roleWidth, roleInfoGHeight, 0, false).draw(g);
    // 绘制自身
    new MainFrameRoleModule(this.uiService, this.resource, this.iX + this.iWidth - roleWidth, this.iY + this.iHeight / 2, roleWidth, roleHeight, 0, true).draw(g);
    new RoleInfoModule(this.uiService, this.resource, this.iX + this.iWidth - roleWidth, this.iY + this.iHeight / 2 + roleHeight, roleWidth, roleInfoGHeight, 0, true).draw(g);
    // 绘制边框
    super.drawWindow(g);
  }
}
