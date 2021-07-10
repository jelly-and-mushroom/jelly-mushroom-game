package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameRoleEnum;
import team.jellymushroom.fullmoon.entity.ui.UIResourceEntity;
import team.jellymushroom.fullmoon.service.MainService;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 角色模块
 *
 * iWidth=98,iHeight=348
 */
public class RoleModule extends Module {

  private GameRoleEnum gameRoleEnum;

  public RoleModule(MainService mainService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, GameRoleEnum gameRoleEnum) {
    super(mainService, resource, oX, oY, oWidth, oHeight, padding);
    this.gameRoleEnum = gameRoleEnum;
  }

  @Override
  public void draw(Graphics g) {
    super.drawWindow(g);
    BufferedImage roleImg = this.resource.getGameDimRoleImgMap().get(gameRoleEnum);
    g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 697, 371, 922, 1393, null);
//    g.drawImage(roleImg, this.iX, this.iY, this.iX + 98, this.iY + 120, 478, 371, 1140, 1393, null);
  }
}
