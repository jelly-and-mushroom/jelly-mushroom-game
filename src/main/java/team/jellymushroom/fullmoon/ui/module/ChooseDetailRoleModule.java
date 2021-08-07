package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChooseDetailRoleModule extends Module {

  private Integer roleIndex;

  public ChooseDetailRoleModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, Integer roleIndex) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.roleIndex = roleIndex;
  }

  @Override
  public void draw(Graphics g) {
    // 图片素材
    BufferedImage roleImg = this.resource.getGameRoleImgMap().get(this.roleIndex);
    // 绘制角色
    g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 478, 371, 1140, 1393, null);
    // 绘制边框
    super.drawWindow(g);
  }
}
