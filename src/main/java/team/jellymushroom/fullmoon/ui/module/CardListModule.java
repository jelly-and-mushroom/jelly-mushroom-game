package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.util.List;

public class CardListModule extends Module {

  private PrepareOptionEnum prepareOption;

  public static final Integer CARD_COLUMN = 4;

  private static final Integer CARD_ROW = 2;

  public CardListModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, PrepareOptionEnum prepareOption) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.prepareOption = prepareOption;
  }

  @Override
  public void draw(Graphics g) {
    // 底色
    super.drawFillRect(g, this.iX, this.iY, this.iWidth, this.iHeight, Color.DARK_GRAY);
    // 第一行卡牌纵坐标
    int firstRowY = this.iY + 65;
    // 卡牌宽高
    int cardWidth = this.iWidth / CARD_COLUMN;
    int cardHeight = (int)(1.0 * cardWidth * CardModule.CARD_SOURCE_HEIGHT / CardModule.CARD_SOURCE_WIDHT);
    // 待展示的卡牌列表
    List<CardEntity> cardList = null;
    if (PrepareOptionEnum.MY_CARD_REPOSITORY.equals(this.prepareOption)) {
      cardList = this.uiService.getMainService().getPlayerMyself().getCardList();
    }
    // 当前高亮卡牌在列表中的index
    int highLightCardIndex = ServerControlEntity.getInstance().getPrepareCardListIndex();
    // 一屏总计展示个数
    int totalCount = CARD_COLUMN * CARD_ROW;
    // 起始展示的卡牌在列表中的index
    int startIndex = totalCount * (highLightCardIndex / totalCount);
    int endIndex = startIndex + totalCount - 1;
    if (endIndex >= cardList.size()) {
      endIndex = cardList.size() - 1;
    }
    // 循环展示卡牌
    int rowIndex = 0;
    int columnIndex = 0;
    for (int i = startIndex; i <= endIndex; i++) {
      boolean highLight = i==highLightCardIndex ? true : false;
      new CardModule(this.uiService, this.resource, this.iX + columnIndex * cardWidth, firstRowY + rowIndex * cardHeight, cardWidth, cardHeight, 0, cardList.get(i).getIndex(), highLight).draw(g);
      columnIndex++;
      if (columnIndex == CARD_COLUMN) {
        columnIndex = 0;
        rowIndex++;
      }
    }
    // 标题
    this.drawTitle(g, cardList.size(), startIndex, totalCount);
    // 绘制边框
    super.drawWindow(g);
  }

  private void drawTitle(Graphics g, int cardListSize, int startIndex, int pageSize) {
    int yAdd = 43;
    int fontSize = 20;
    int fontStyle = Font.PLAIN;
    int totalpage = cardListSize / pageSize;
    if (cardListSize % pageSize != 0) {
      totalpage++;
    }
    int currentPage = startIndex / pageSize + 1;
    if (PrepareOptionEnum.MY_CARD_REPOSITORY.equals(this.prepareOption)) {
      super.drawFont(g, this.iX + 200, this.iY + yAdd, this.prepareOption.getDescription() + "(总计:" + cardListSize + "张)[第" + currentPage+"/" + totalpage + "页]", Color.WHITE, fontStyle, fontSize);
      return;
    }
  }
}
