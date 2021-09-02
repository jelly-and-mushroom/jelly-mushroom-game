package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.game.GameBlessingEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;
import java.util.List;

public class ChooseBlessingModule extends Module {

  public static final Integer BLESSING_COUNT = 6;

  public ChooseBlessingModule(UIService uiService, UIResourceEntity resource, Integer oX, Integer oY, Integer oWidth, Integer oHeight, Integer padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  void draw(Graphics g) {
    // 底色
    super.drawFillRect(g, this.iX, this.iY, this.iWidth, this.iHeight, Color.DARK_GRAY);
    // 标题
    super.drawFont(g, this.iX + 240, this.iY + 43, "选择祝福", Color.WHITE, Font.PLAIN, 20);
    // 祝福
    PlayerEntity player = this.uiService.getMainService().getPlayerMyself();
    List<GameBlessingEntity> blessingList = player.getSignal().getBlessingList();
    if (!blessingList.isEmpty()) {
      int chooseIndex = player.getSignal().getIndex();
      int blessingHeight = BlessingModule.getHeight(this.resource, blessingList.get(0).getIndex(), false, this.iWidth);
      int firstRowY = this.iY + 65;
      for (int i = 0; i < blessingList.size(); i++) {
        new BlessingModule(this.uiService, this.resource, this.iX, firstRowY + i * blessingHeight, this.iWidth, blessingHeight, 0, blessingList.get(i), false, i == chooseIndex).draw(g);
      }
    }
    // 绘制边框
    super.drawWindow(g);
  }
}
