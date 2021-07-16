package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RoleModule extends Module {

  private GameRoleEntity gameRoleEntity;

  private Boolean light;

  private Boolean detail;

  private Boolean confirm;

  private Integer confirmX;

  private static final Integer CONFIRM_SIZE = 30;

  public RoleModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, GameRoleEntity gameRoleEntity, Boolean light, Boolean detail, Boolean confirm) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.gameRoleEntity = gameRoleEntity;
    this.light = light;
    this.detail = detail;
    this.confirm = confirm;
    this.confirmX = this.iX + (this.iWidth - CONFIRM_SIZE);
  }

  @Override
  public void draw(Graphics g) {
    // 图片素材
    BufferedImage roleImg = this.light ? this.resource.getGameRoleImgMap().get(this.gameRoleEntity.getIndex()) : this.resource.getGameDimRoleImgMap().get(this.gameRoleEntity.getIndex());
    BufferedImage confirmImg = resource.getConfirmImg();
    // 绘制角色
    if (this.detail) {
      g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 478, 371, 1140, 1393, null);
      return;
    } else {
      g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 697, 371, 922, 1393, null);
      if (confirm) {
        g.drawImage(confirmImg, this.confirmX, this.iY, this.confirmX + CONFIRM_SIZE, this.iY + CONFIRM_SIZE, 0, 0, confirmImg.getWidth(), confirmImg.getHeight(), null);
      }
    }
    // 绘制边框
    super.drawWindow(g);
  }
}
