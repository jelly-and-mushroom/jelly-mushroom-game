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
    // 绘制对手角色
    new DetailRoleModule(this.uiService, this.resource, this.iX + this.iWidth - DetailRoleModule.O_WIDTH, this.iY, DetailRoleModule.O_WIDTH, DetailRoleModule.O_HEIGHT, 0, false).draw(g);
    // 绘制自身角色
    new DetailRoleModule(this.uiService, this.resource, this.iX + this.iWidth - DetailRoleModule.O_WIDTH, this.iY + DetailRoleModule.O_HEIGHT, DetailRoleModule.O_WIDTH, DetailRoleModule.O_HEIGHT, 0, true).draw(g);
    // 绘制边框
    super.drawWindow(g);
  }
}
