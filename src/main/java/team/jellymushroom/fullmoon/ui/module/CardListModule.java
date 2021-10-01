package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;
import team.jellymushroom.fullmoon.stagehandler.PrepareBuyCardStageHandler;
import team.jellymushroom.fullmoon.stagehandler.PrepareDeleteCardStageHandler;
import team.jellymushroom.fullmoon.stagehandler.PrepareIntensifyCardStageHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CardListModule extends Module {

  private PrepareOptionEnum prepareOption;

  public static final Integer CARD_COLUMN = 4;

  public static final Integer CARD_ROW = 2;

  public CardListModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, PrepareOptionEnum prepareOption) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.prepareOption = prepareOption;
  }

  @Override
  public void draw(Graphics g) {
    // 底色
    super.drawFillRect(g, this.iX, this.iY, this.iWidth, this.iHeight, Color.DARK_GRAY);
    // 待展示的卡牌列表
    List<CardEntity> cardList = null;
    if (PrepareOptionEnum.MY_CARD_REPOSITORY.equals(this.prepareOption) || PrepareOptionEnum.DELETE_CARD.equals(this.prepareOption)) {
      cardList = this.uiService.getMainService().getPlayerMyself().getCardList();
    } else if (PrepareOptionEnum.BY_CARD.equals(this.prepareOption)) {
      cardList = this.uiService.getMainService().getPlayerMyself().getSignal().getCardList();
    } else if (PrepareOptionEnum.INTENSIFY_CARD.equals(this.prepareOption)) {
      cardList = new ArrayList<>();
      List<Integer> intensifyIndexList = this.uiService.getMainService().getPlayerMyself().getIntensifyCardIndexList();
      for (Integer intensifyIndex : intensifyIndexList) {
        cardList.add(this.uiService.getMainService().getPlayerMyself().getCardList().get(intensifyIndex));
      }
    }
    // 一屏总计展示个数
    int totalCount = CARD_COLUMN * CARD_ROW;
    // 起始展示的卡牌在列表中的index
    int startIndex = 0;
    // 卡牌列表不为空时才展示
    if (null != cardList && !cardList.isEmpty()) {
      // 第一行卡牌纵坐标
      int firstRowY = this.iY + 65;
      // 卡牌宽高
      int cardWidth = this.iWidth / CARD_COLUMN;
      int cardHeight = CardModule.getHeight(cardWidth);
      // 当前高亮卡牌在列表中的index
      int highLightCardIndex = this.uiService.getMainService().getPlayerMyself().getSignal().getIndex();
      // 起始展示的卡牌在列表中的index
      startIndex = totalCount * (highLightCardIndex / totalCount);
      int endIndex = startIndex + totalCount - 1;
      if (endIndex >= cardList.size()) {
        endIndex = cardList.size() - 1;
      }
      // 循环展示卡牌
      int rowIndex = 0;
      int columnIndex = 0;
      for (int i = startIndex; i <= endIndex; i++) {
        boolean highLight = i==highLightCardIndex ? true : false;
        new CardModule(this.uiService, this.resource, this.iX + columnIndex * cardWidth, firstRowY + rowIndex * cardHeight, cardWidth, cardHeight, 0, cardList.get(i), highLight).draw(g);
        columnIndex++;
        if (columnIndex == CARD_COLUMN) {
          columnIndex = 0;
          rowIndex++;
        }
      }
    }
    // 标题
    this.drawTitle(g, cardList.size(), startIndex, totalCount);
    // 绘制边框
    super.drawWindow(g);
    // 绘制detail
    this.drawCardDetail(g);
    // 绘制购买Random
    this.drawBuyRandow(g);
  }

  private void drawBuyRandow(Graphics g) {
    if (!GameStageEnum.PREPARE_BUY_CARD_RANDOM.equals(this.uiService.getMainService().getPlayerMyself().getStage())) {
      return;
    }
    List<CardEntity> cardList = this.uiService.getMainService().getPlayerMyself().getSignal().getCardList2();
    int cardCount = PrepareBuyCardStageHandler.RANDOM_BUY_SIZE;
    if (cardList.size() != cardCount) {
      return;
    }
    int cardWidth = this.iWidth / cardCount;
    int cardHeight = CardModule.getHeight(cardWidth);
    int cardX = this.iX;
    int cardY = this.iY + (this.iHeight - cardHeight) / 2;
    for (CardEntity card : cardList) {
      new CardModule(this.uiService, this.resource, cardX, cardY, cardWidth, cardHeight, 0, card, true).draw(g);
      cardX += cardWidth;
    }
  }

  private void drawCardDetail(Graphics g) {
    GameStageEnum stage = this.uiService.getMainService().getPlayerMyself().getStage();
    PlayerEntity player = this.uiService.getMainService().getPlayerMyself();
    Integer cardIndex = player.getSignal().getIndex();
    CardEntity card = null;
    if (GameStageEnum.PREPARE_MY_CARD_REPOSITORY_DETAIL.equals(stage) || GameStageEnum.PREPARE_DELETE_CARD_DETAIL.equals(stage)) {
      card = player.getCardList().get(cardIndex);
    } else if (GameStageEnum.PREPARE_BUY_CARD_DETAIL.equals(stage)) {
      card = player.getSignal().getCardList().get(cardIndex);
    } else if (GameStageEnum.PREPARE_INTENSIFY_CARD_DETAIL.equals(stage)) {
      card = player.getCardList().get(player.getIntensifyCardIndexList().get(cardIndex));
    } else {
      return;
    }
    int detailCardWidth = 300;
    int detailCardHeight = CardModule.getHeight(detailCardWidth);;
    new CardModule(this.uiService, this.resource,
        this.iX + (this.iWidth - detailCardWidth) / 2,
        this.iY + (this.iHeight - detailCardHeight) / 2,
        detailCardWidth, detailCardHeight, 0,
        card,
        true).draw(g);
  }

  private void drawTitle(Graphics g, int cardListSize, int startIndex, int pageSize) {
    int yAdd = 43;
    int fontSize = 20;
    int fontStyle = Font.PLAIN;
    if (PrepareOptionEnum.MY_CARD_REPOSITORY.equals(this.prepareOption)) {
      int currentPage = cardListSize==0 ? 0 : startIndex / pageSize + 1;
      int totalpage = cardListSize / pageSize;
      if (cardListSize % pageSize != 0) {
        totalpage++;
      }
      int equipmentInSoltSize = this.uiService.getMainService().getPlayerMyself().getEquipmentInSoltSize();
      int slotSize = this.uiService.getMainService().getPlayerMyself().getInitEquipmentSlotSize();
      super.drawFont(g, this.iX + 140, this.iY + yAdd, this.prepareOption.getDescription() + "(总计:" + cardListSize + "张)", Color.WHITE, fontStyle, fontSize);
      Color limitColor = equipmentInSoltSize<slotSize ? Color.CYAN : Color.PINK;
      super.drawFont(g, this.iX + 335, this.iY + yAdd, "[装备槽:" + equipmentInSoltSize + "/" + slotSize + "]", limitColor, fontStyle, fontSize);
      super.drawFont(g, this.iX + 460, this.iY + yAdd, "[第" + currentPage + "/" + totalpage + "页]", Color.WHITE, fontStyle, fontSize);
      return;
    }
    if (PrepareOptionEnum.BY_CARD.equals(this.prepareOption)) {
      super.drawFont(g, this.iX + 300, this.iY + yAdd, this.prepareOption.getDescription(), Color.WHITE, fontStyle, fontSize);
      List<CardEntity> cardList = this.uiService.getMainService().getPlayerMyself().getSignal().getCardList();
      if (!cardList.isEmpty()) {
        CardEntity card = cardList.get(this.uiService.getMainService().getPlayerMyself().getSignal().getIndex());
        super.drawFont(g, this.iX + 400, this.iY + yAdd, "(" + card.getPrice() + ")", Color.ORANGE, fontStyle, fontSize);
      }
      return;
    }
    if (PrepareOptionEnum.INTENSIFY_CARD.equals(this.prepareOption)) {
      super.drawFont(g, this.iX + 300, this.iY + yAdd, this.prepareOption.getDescription(), Color.WHITE, fontStyle, fontSize);
      if (cardListSize > 0) {
        super.drawFont(g, this.iX + 400, this.iY + yAdd, "(" + PrepareIntensifyCardStageHandler.PRICE + ")", Color.ORANGE, fontStyle, fontSize);
      }
      return;
    }
    if (PrepareOptionEnum.DELETE_CARD.equals(this.prepareOption)) {
      super.drawFont(g, this.iX + 300, this.iY + yAdd, this.prepareOption.getDescription(), Color.WHITE, fontStyle, fontSize);
      if (cardListSize > 0) {
        int deleteCost = this.uiService.getMainService().getPlayerMyself().getDeleteUncostTimes() > 0 ? 0 : PrepareDeleteCardStageHandler.PRICE;
        super.drawFont(g, this.iX + 400, this.iY + yAdd, "(" + deleteCost + ")", Color.ORANGE, fontStyle, fontSize);
      }
      return;
    }
  }
}
