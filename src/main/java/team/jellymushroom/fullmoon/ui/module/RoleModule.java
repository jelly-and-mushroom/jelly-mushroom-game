package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameRoleEnum;
import team.jellymushroom.fullmoon.entity.ui.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 角色模块
 *
 * iWidth=98,iHeight=348
 * g.drawImage(roleImg, this.iX, this.iY, this.iX + 98, this.iY + 120, 478, 371, 1140, 1393, null);
 */
public class RoleModule extends Module {

  private GameRoleEnum gameRoleEnum;

  private Boolean light;

  private Boolean detail;

  public RoleModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, GameRoleEnum gameRoleEnum, Boolean light, Boolean detail) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.gameRoleEnum = gameRoleEnum;
    this.light = light;
    this.detail = detail;
  }

  @Override
  public void draw(Graphics g) {
    super.drawWindow(g);

    BufferedImage roleImg = this.light ? this.resource.getGameRoleImgMap().get(gameRoleEnum) : this.resource.getGameDimRoleImgMap().get(gameRoleEnum);
    if (this.detail) {
      g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 478, 371, 1140, 1393, null);
    } else {
      g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 697, 371, 922, 1393, null);
    }
    GameRoleEnum confirmedRole = this.uiService.getGame().getMySelf().getGameRoleEnum();
    if (this.gameRoleEnum.equals(confirmedRole)) {

    }
  }
}
