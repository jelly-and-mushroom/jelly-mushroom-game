package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

public class PrepareOptionModule extends Module {

  private PrepareOptionEnum prepareOption;

  public PrepareOptionModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, PrepareOptionEnum prepareOption) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.prepareOption = prepareOption;
  }

  @Override
  public void draw(Graphics g) {
    // 绘制底色
    Color rectColor = this.prepareOption.equals(this.uiService.getMainService().getPlayerMyself().getSignal().getPrepareOption()) ? Color.GRAY : Color.DARK_GRAY;
    super.drawFillRect(g, this.iX, this.iY, this.iWidth, this.iHeight, rectColor);
    // 绘制文字
    this.drawOptionFont(g);
    // 绘制边框
    super.drawWindow(g);
  }

  private void drawOptionFont(Graphics g) {
    int yAdd = 43;
    int fontSize = 20;
    int fontStyle = Font.PLAIN;
    if (PrepareOptionEnum.DONE.equals(this.prepareOption)) {
      super.drawFont(g, this.iX + 300, this.iY + yAdd, this.prepareOption.getDescription(), Color.WHITE, fontStyle, fontSize);
      return;
    }
    if (PrepareOptionEnum.INTENSIFY_CARD.equals(this.prepareOption) || PrepareOptionEnum.DELETE_CARD.equals(this.prepareOption)) {
      super.drawFont(g, this.iX + 70, this.iY + yAdd, this.prepareOption.getDescription(), Color.WHITE, fontStyle, fontSize);
      return;
    }
    int currentValue = this.getCurrentValue();
    if (PrepareOptionEnum.PROMOTE_EQUIPMENT_SLOT.equals(this.prepareOption)) {
      super.drawFont(g, this.iX + 25, this.iY + yAdd, this.prepareOption.getDescription() + this.prepareOption.getValue() , Color.WHITE, fontStyle, fontSize);
      super.drawFont(g, this.iX + 120, this.iY + yAdd, "[" + currentValue + "]", Color.CYAN, fontStyle, fontSize);
      super.drawFont(g, this.iX + 165, this.iY + yAdd, "(" + this.prepareOption.getPrice() + ")" , Color.ORANGE, fontStyle, fontSize);
      return;
    }
    if (this.prepareOption.getValue() == -1) {
      super.drawFont(g, this.iX + 125, this.iY + yAdd, this.prepareOption.getDescription(), Color.WHITE, fontStyle, fontSize);
      return;
    }
    if (this.prepareOption.getLimitValue() == -1) {
      super.drawFont(g, this.iX + 25, this.iY + yAdd, this.prepareOption.getDescription() + this.prepareOption.getValue() , Color.WHITE, fontStyle, fontSize);
      super.drawFont(g, this.iX + 165, this.iY + yAdd, "(" + this.prepareOption.getPrice() + ")" , Color.ORANGE, fontStyle, fontSize);
      return;
    }
    Color limitColor = currentValue<this.prepareOption.getLimitValue() ? Color.CYAN : Color.PINK;
    super.drawFont(g, this.iX + 10, this.iY + yAdd, this.prepareOption.getDescription() + this.prepareOption.getValue(), Color.WHITE, fontStyle, fontSize);
    super.drawFont(g, this.iX + 120, this.iY + yAdd, "[" + currentValue + "/" + this.prepareOption.getLimitValue() + "]", limitColor, fontStyle, fontSize);
    super.drawFont(g, this.iX + 170, this.iY + yAdd, "(" + this.prepareOption.getPrice() + ")", Color.ORANGE, fontStyle, fontSize);
  }

  private int getCurrentValue() {
    if (PrepareOptionEnum.PROMOTE_INIT_HAND_CARD.equals(this.prepareOption)) {
      return this.uiService.getMainService().getPlayerMyself().getInitHandCardSize();
    }
    if (PrepareOptionEnum.PROMOTE_MAX_HAND_CARD_SIZE.equals(this.prepareOption)) {
      return this.uiService.getMainService().getPlayerMyself().getMaxHandCardSize();
    }
    if (PrepareOptionEnum.PROMOTE_DRAW_CARD_SIZE.equals(this.prepareOption)) {
      return this.uiService.getMainService().getPlayerMyself().getDrawCardSize();
    }
    if (PrepareOptionEnum.PROMOTE_EQUIPMENT_SLOT.equals(this.prepareOption)) {
      return this.uiService.getMainService().getPlayerMyself().getInitEquipmentSlotSize();
    }
    return -1;
  }
}
