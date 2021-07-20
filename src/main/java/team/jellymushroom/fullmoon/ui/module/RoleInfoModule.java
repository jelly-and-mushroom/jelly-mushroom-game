package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

public class RoleInfoModule extends Module {

  private Boolean mySelf;

  public RoleInfoModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, Boolean mySelf) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.mySelf = mySelf;
  }

  @Override
  public void draw(Graphics g) {
    // 绘制边框
    super.drawWindow(g);
  }
}
