package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

public class PrepareModule extends Module {

  public PrepareModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    // 绘制背景图片
    super.drawBackgroundImg(g);
    // 绘制对手角色
    new DetailRoleModule(this.uiService, this.resource, 324, 108, 376, 580, 0, this.uiService.getMainService().getPlayerOpponent().getGameRoleEntity()).draw(g);
    // 绘制边框
    super.drawWindow(g);
  }
}
