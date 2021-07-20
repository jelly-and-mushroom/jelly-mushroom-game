package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrameRoleModule extends Module {

  private Boolean mySelf;

  public MainFrameRoleModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, Boolean mySelf) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.mySelf = mySelf;
  }

  @Override
  public void draw(Graphics g) {
    // 获取对应玩家
    PlayerEntity player = this.mySelf ? this.uiService.getMainService().getPlayerMyself() : this.uiService.getMainService().getPlayerOpponent();
    // 图片素材
    BufferedImage roleImg = this.resource.getGameRoleImgMap().get(player.getGameRoleEntity().getIndex());
    // 绘制角色
    g.drawImage(roleImg, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 518, 420, 1095, 1001, null);
    // 绘制边框
    super.drawWindow(g);
  }
}
