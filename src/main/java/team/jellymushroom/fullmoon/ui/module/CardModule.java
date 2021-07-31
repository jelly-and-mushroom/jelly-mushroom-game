package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CardModule extends Module {

  private CardEntity card;

  private Boolean showWindow;

  public static final Integer CARD_SOURCE_WIDHT = 560;

  public static final Integer CARD_SOURCE_HEIGHT = 842;

  public CardModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, CardEntity card, Boolean showWindow) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.iX = this.oX + this.padding;
    this.iY = this.oY + this.padding;
    this.iWidth = this.oWidth - 2 * this.padding;
    this.iHeight = this.oHeight - 2 * this.padding;
    this.card = card;
    this.showWindow = showWindow;
  }

  @Override
  public void draw(Graphics g) {
    BufferedImage img = this.resource.getCardImgMap().get(card.getIndex());
    g.drawImage(img, this.iX, this.iY, this.iX + iWidth, this.iY + iHeight, 486, 305, 1046, 1147, null);
    if (card instanceof EquipmentCardEntity) {
      EquipmentCardEntity equipmentCard = (EquipmentCardEntity)card;
      if (equipmentCard.getPlace()) {
        super.drawFont(g, this.iX + 10, this.iY + 43, "装备中", Color.BLACK, Font.PLAIN, 20);
      }
    }
    if (this.showWindow) {
      super.drawWindow(g);
    }
  }
}
