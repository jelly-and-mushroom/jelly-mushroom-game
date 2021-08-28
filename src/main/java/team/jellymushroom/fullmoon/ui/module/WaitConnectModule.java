package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class WaitConnectModule extends Module {

  public WaitConnectModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    // 绘制背景图片
    super.drawBackgroundImg(g);
    // 绘制等待连接图标
    BufferedImage img = this.resource.getComputerIconImg();
    int waitSize = 100;
    int waitX = 850;
    int waitY = 15;
    g.drawImage(img, this.iX + waitX, this.iY + waitY, this.iX + waitX + waitSize, this.iY + waitY + waitSize, 0, 0, img.getWidth(), img.getHeight(), null);
    // 绘制点
    int interval = 7;
    int diameter = 7;
    int pointXBegin = this.iX + waitX + waitSize + interval;
    int pointYBegin = this.iY + waitY + waitSize / 2 - (diameter / 2);
    int pointCount = this.pointCount();
    for (int i = 0; i < pointCount; i++) {
      g.fillOval(pointXBegin, pointYBegin, diameter, diameter);
      pointXBegin = pointXBegin + diameter + interval;
    }
    this.test(g);
    // 绘制边框
    super.drawWindow(g);
  }

  private void test(Graphics g) {
    Map.Entry<BufferedImage, List<Integer>> imgMap = this.resource.getBlessing(12, true);
    g.drawImage(imgMap.getKey(),
        this.iX + 50,
        this.iY + 50,
        this.iX + 50 + (int)((imgMap.getValue().get(2) - imgMap.getValue().get(0))),
        this.iY + 50 + (int)((imgMap.getValue().get(3) - imgMap.getValue().get(1))),
        imgMap.getValue().get(0),
        imgMap.getValue().get(1),
        imgMap.getValue().get(2),
        imgMap.getValue().get(3),
        null);
  }

  private int pointCount() {
    long timePass = System.currentTimeMillis() - this.uiService.getMainService().getInitTime();
    return  ((int)(timePass / 1000)) % 4;
  }
}
