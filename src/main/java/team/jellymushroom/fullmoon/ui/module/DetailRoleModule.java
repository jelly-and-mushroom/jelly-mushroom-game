package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DetailRoleModule extends Module {

  private GameRoleEntity gameRoleEntity;

  public DetailRoleModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, GameRoleEntity gameRoleEntity) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.gameRoleEntity = gameRoleEntity;
  }

  @Override
  public void draw(Graphics g) {
    // 图片素材
    BufferedImage roleImg = this.resource.getGameRoleImgMap().get(this.gameRoleEntity.getIndex());
    // 绘制角色
    g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 478, 371, 1140, 1000, null);
    // 绘制边框
    super.drawWindow(g);
  }
}
