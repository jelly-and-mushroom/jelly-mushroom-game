package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.constant.PrepareOptionEnum;
import team.jellymushroom.fullmoon.entity.game.GameBlessingEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.util.List;

public class BlessingListModule extends Module {

  public static final Integer BLESSING_COUNT = 6;

  public BlessingListModule(UIService uiService, UIResourceEntity resource, Integer oX, Integer oY, Integer oWidth, Integer oHeight, Integer padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  void draw(Graphics g) {
    // 底色
    super.drawFillRect(g, this.iX, this.iY, this.iWidth, this.iHeight, Color.DARK_GRAY);
    // 祝福
    PlayerEntity player = this.uiService.getMainService().getPlayerMyself();
    GameStageEnum stage = this.uiService.getMainService().getPlayerMyself().getStage();
    List<GameBlessingEntity> blessingList = null;
    if (GameStageEnum.CHOOSE_BLESSING.equals(stage)) {
      blessingList = player.getSignal().getBlessingList();
    } else {
      blessingList = player.getGameBlessingList();
    }
    int startIndex = 0;
    if (!blessingList.isEmpty()) {
      int chooseIndex = player.getSignal().getIndex();
      int blessingHeight = BlessingModule.getHeight(this.resource, blessingList.get(0).getIndex(), false, this.iWidth);
      int firstRowY = this.iY + 65;
      startIndex = BlessingListModule.BLESSING_COUNT * (chooseIndex / BlessingListModule.BLESSING_COUNT);
      int endIndex = startIndex + BlessingListModule.BLESSING_COUNT - 1;
      if (endIndex >= blessingList.size()) {
        endIndex = blessingList.size() - 1;
      }
      for (int i = startIndex; i <= endIndex; i++) {
        int row = i % BlessingListModule.BLESSING_COUNT;
        new BlessingModule(this.uiService, this.resource, this.iX, firstRowY + row * blessingHeight, this.iWidth, blessingHeight, 0, blessingList.get(i), false, i == chooseIndex).draw(g);
      }
    }
    // 标题
    this.drawTitle(g, blessingList.size(), startIndex);
    // 绘制边框
    super.drawWindow(g);
  }

  private void drawTitle(Graphics g, int blessingListSize, int startIndex) {
    int yAdd = 43;
    Color color = Color.WHITE;
    int fontStyle = Font.PLAIN;
    int fontSize = 20;
    if (GameStageEnum.CHOOSE_BLESSING.equals(this.uiService.getMainService().getPlayerMyself().getStage())) {
      super.drawFont(g, this.iX + 240, this.iY + yAdd, "选择祝福", color, fontStyle, fontSize);
      return;
    }
    int currentPage = blessingListSize==0 ? 0 : startIndex / BlessingListModule.BLESSING_COUNT + 1;
    int totalpage = blessingListSize / BlessingListModule.BLESSING_COUNT;
    if (blessingListSize % BlessingListModule.BLESSING_COUNT != 0) {
      totalpage++;
    }
    super.drawFont(g, this.iX + 165, this.iY + yAdd, PrepareOptionEnum.MY_BLESSING.getDescription() + "(总计:" + blessingListSize + ")[第" + currentPage + "/" + totalpage + "页]", color, fontStyle, fontSize);
  }
}
