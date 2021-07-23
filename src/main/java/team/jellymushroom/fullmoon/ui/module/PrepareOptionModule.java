package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

public class PrepareOptionModule extends Module {

  public PrepareOptionModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    // 绘制底色
    super.drawFillRect(g, this.iX, this.iY, this.iWidth, this.iHeight, Color.DARK_GRAY);
    // 绘制边框
    super.drawWindow(g);
  }
}
