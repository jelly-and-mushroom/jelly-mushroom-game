package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundModule extends Module {

  public BackgroundModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    // 绘制等待连接图片
    BufferedImage img = resource.getWaitConnectImg();
    g.drawImage(img, this.iX, this.iY, this.iX + this.iWidth, this.iY + this.iHeight, 95, 0, 1761, img.getHeight(), null);
    // 绘制边框
    super.drawWindow(g);
  }
}
