package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameRoleEnum;
import team.jellymushroom.fullmoon.entity.ui.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 角色模块
 */
public class RoleModule extends Module {

  private GameRoleEnum gameRoleEnum;

  private Boolean light;

  private Boolean detail;

  private Boolean confirm;

  private Integer confirmSize = 30;

  private Integer confirmX;

  public RoleModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, GameRoleEnum gameRoleEnum, Boolean light, Boolean detail, Boolean confirm) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.gameRoleEnum = gameRoleEnum;
    this.light = light;
    this.detail = detail;
    this.confirm = confirm;
    this.confirmX = this.iX + (this.iWidth - this.confirmSize);
  }

  @Override
  public void draw(Graphics g) {
    super.drawWindow(g);
    BufferedImage roleImg = this.light ? this.resource.getGameRoleImgMap().get(gameRoleEnum) : this.resource.getGameDimRoleImgMap().get(gameRoleEnum);
    BufferedImage confirmImg = resource.getConfirmImg();
    if (this.detail) {
      g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 478, 371, 1140, 1393, null);
      if (confirm) {
        g.drawImage(confirmImg, this.confirmX, this.iY, this.confirmX + confirmSize, this.iY + confirmSize, 0, 0, confirmImg.getWidth(), confirmImg.getHeight(), null);
      }
      return;
    }
    g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 697, 371, 922, 1393, null);
    if (confirm) {
      g.drawImage(confirmImg, this.confirmX, this.iY, this.confirmX + confirmSize, this.iY + confirmSize, 0, 0, confirmImg.getWidth(), confirmImg.getHeight(), null);
    }
  }
}
