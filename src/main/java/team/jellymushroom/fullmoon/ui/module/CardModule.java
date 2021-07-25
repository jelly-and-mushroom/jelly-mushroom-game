package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CardModule extends Module {

  private Integer cardIndex;

  private Boolean showWindow;

  public static final Integer CARD_SOURCE_WIDHT = 560;

  public static final Integer CARD_SOURCE_HEIGHT = 842;

  public CardModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, Integer cardIndex, Boolean showWindow) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.iX = this.oX + this.padding;
    this.iY = this.oY + this.padding;
    this.iWidth = this.oWidth - 2 * this.padding;
    this.iHeight = this.oHeight - 2 * this.padding;
    this.cardIndex = cardIndex;
    this.showWindow = showWindow;
  }

  @Override
  public void draw(Graphics g) {
    BufferedImage img = this.resource.getCardImgMap().get(cardIndex);
    g.drawImage(img, this.iX, this.iY, this.iX + iWidth, this.iY + iHeight, 486, 305, 1046, 1147, null);
    if (this.showWindow) {
      super.drawWindow(g);
    }
  }
}
