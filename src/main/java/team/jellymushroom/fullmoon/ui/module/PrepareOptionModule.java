package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.PrepareEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

public class PrepareOptionModule extends Module {

  private PrepareEnum prepareEnum;

  public PrepareOptionModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, PrepareEnum prepareEnum) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.prepareEnum = prepareEnum;
  }

  @Override
  public void draw(Graphics g) {
    // 绘制底色
    Color rectColor = this.prepareEnum.equals(ServerControlEntity.getInstance().getCurrentPrepare()) ? Color.GRAY : Color.DARK_GRAY;
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
    if (PrepareEnum.DONE.equals(this.prepareEnum)) {
      super.drawFont(g, this.iX + 300, this.iY + yAdd, this.prepareEnum.getDescription(), Color.WHITE, fontStyle, fontSize);
      return;
    }
    if (this.prepareEnum.getValue() == -1) {
      super.drawFont(g, this.iX + 125, this.iY + yAdd, this.prepareEnum.getDescription(), Color.WHITE, fontStyle, fontSize);
      return;
    }
    if (this.prepareEnum.getLimitValue() == -1) {
      super.drawFont(g, this.iX + 25, this.iY + yAdd, this.prepareEnum.getDescription() + this.prepareEnum.getValue() , Color.WHITE, fontStyle, fontSize);
      super.drawFont(g, this.iX + 165, this.iY + yAdd, "(" + this.prepareEnum.getPrice() + ")" , Color.ORANGE, fontStyle, fontSize);
      return;
    }
    int currentValue = this.getCurrentValue();
    Color limitColor = currentValue<this.prepareEnum.getLimitValue() ? Color.CYAN : Color.PINK;
    super.drawFont(g, this.iX + 10, this.iY + yAdd, this.prepareEnum.getDescription() + this.prepareEnum.getValue(), Color.WHITE, fontStyle, fontSize);
    super.drawFont(g, this.iX + 120, this.iY + yAdd, "[" + currentValue + "/" + this.prepareEnum.getLimitValue() + "]", limitColor, fontStyle, fontSize);
    super.drawFont(g, this.iX + 170, this.iY + yAdd, "(" + this.prepareEnum.getPrice() + ")", Color.ORANGE, fontStyle, fontSize);
  }

  private int getCurrentValue() {
    if (PrepareEnum.PROMOTE_INIT_HAND_CARD.equals(this.prepareEnum)) {
      return this.uiService.getMainService().getPlayerMyself().getInitHandCardSize();
    }
    if (PrepareEnum.PROMOTE_MAX_HAND_CARD_SIZE.equals(this.prepareEnum)) {
      return this.uiService.getMainService().getPlayerMyself().getMaxHandCardSize();
    }
    if (PrepareEnum.PROMOTE_DRAW_CARD_SIZE.equals(this.prepareEnum)) {
      return this.uiService.getMainService().getPlayerMyself().getDrawCardSize();
    }
    return -1;
  }
}
