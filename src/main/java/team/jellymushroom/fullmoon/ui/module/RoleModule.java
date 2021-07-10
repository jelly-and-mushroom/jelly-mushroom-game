package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.ui.UIResourceEntity;

import java.awt.*;

/**
 * 角色模块
 */
public class RoleModule extends Module {

  public RoleModule(UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    super.drawWindow(g);
  }
}
