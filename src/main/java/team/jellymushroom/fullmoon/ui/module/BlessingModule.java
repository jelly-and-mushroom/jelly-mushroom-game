package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.game.GameBlessingEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class BlessingModule extends Module {

  private GameBlessingEntity blessing;

  private Boolean part;

  private Boolean showWindow;

  public BlessingModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, GameBlessingEntity blessing, Boolean part, Boolean showWindow) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.iX = this.oX + this.padding;
    this.iY = this.oY + this.padding;
    this.iWidth = this.oWidth - 2 * this.padding;
    this.iHeight = this.oHeight - 2 * this.padding;
    this.blessing = blessing;
    this.part = part;
    this.showWindow = showWindow;
  }

  @Override
  public void draw(Graphics g) {
    Map.Entry<BufferedImage, List<Integer>> imgEntry = this.resource.getBlessing(blessing.getIndex(), this.part);
    g.drawImage(imgEntry.getKey(), this.iX, this.iY, this.iX + iWidth, this.iY + iHeight, imgEntry.getValue().get(0), imgEntry.getValue().get(1), imgEntry.getValue().get(2), imgEntry.getValue().get(3), null);
    if (this.showWindow) {
      super.drawWindow(g);
    }
  }

  public static int getHeight(UIResourceEntity resource, int blessingIndex, boolean part, int width) {
    Map.Entry<BufferedImage, List<Integer>> imgEntry = resource.getBlessing(blessingIndex, part);
    int sourceWidth = imgEntry.getValue().get(2) - imgEntry.getValue().get(0);
    int sourceHeight = imgEntry.getValue().get(3) - imgEntry.getValue().get(1);
    return (int)(1.0 * width * sourceHeight / sourceWidth);
  }
}
