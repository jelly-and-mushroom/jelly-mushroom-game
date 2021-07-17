package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

public class WaitConnectModule extends Module {

  public WaitConnectModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    // 绘制文字
    super.drawFont(g, this.iX + 830, this.iY + 700, "连接中" + this.generatePointStr(), Color.WHITE, 35);
  }

  private String generatePointStr() {
    long timePass = System.currentTimeMillis() - ServerControlEntity.getInstance().getInitTime();
    int pointCount = ((int)(timePass / 1000)) % 7;
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < pointCount; i++) {
      result.append(".");
    }
    return result.toString();
  }
}
