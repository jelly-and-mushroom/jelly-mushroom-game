package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

public class RoleInfoModule extends Module {

  private Boolean mySelf;

  public RoleInfoModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding, Boolean mySelf) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
    this.mySelf = mySelf;
  }

  @Override
  public void draw(Graphics g) {
    // 获取对应玩家
    PlayerEntity player = this.mySelf ? this.uiService.getMainService().getPlayerMyself() : this.uiService.getMainService().getPlayerOpponent();
    // 槽位参数
    int slotXInterval = 10;
    int slotWidth = this.iWidth - 2 * slotXInterval;
    int slotHeight = 20;
    int slotYInterval = (this.iHeight - 4 * slotHeight) / 2;
    int slotX = this.iX + slotXInterval;
    int slotY = this.iY + slotYInterval;
    int fontX = this.iX + 50;
    int fontSize = 15;
    int fontY = slotY - (slotHeight - fontSize);
    // hp
    super.drawFillRect(g, slotX, slotY, slotWidth, slotHeight, Color.BLACK);
    super.drawFillRect(g, slotX, slotY, slotWidth, slotHeight, Color.RED);
    super.drawFont(g, fontX, fontY + slotHeight, player.getMaxHp() + "/" + player.getMaxHp(), Color.GREEN, Font.PLAIN, fontSize);
    // mp
    super.drawFillRect(g, slotX, slotY + slotHeight, slotWidth, slotHeight, Color.BLACK);
    super.drawFillRect(g, slotX, slotY + slotHeight, slotWidth, slotHeight, Color.BLUE);
    super.drawFont(g, fontX, fontY + 2 * slotHeight, player.getInitMp() + "", Color.GREEN, Font.PLAIN, fontSize);
    // 行动力
    super.drawFillRect(g, slotX, slotY + 2 * slotHeight, slotWidth, slotHeight, Color.BLACK);
    super.drawFillRect(g, slotX, slotY + 2 * slotHeight, slotWidth, slotHeight, Color.YELLOW);
    super.drawFont(g, fontX, fontY + 3 * slotHeight, player.getMaxAction() + "/" + player.getMaxAction(), Color.GREEN, Font.PLAIN, fontSize);
    // 金币
    super.drawFillRect(g, slotX, slotY + 3 * slotHeight, slotWidth, slotHeight, Color.BLACK);
    super.drawFont(g, fontX, fontY + 4 * slotHeight, "金币:" + player.getGold(), Color.GREEN, Font.PLAIN, fontSize);
    // 绘制边框
    super.drawWindow(g);
  }
}
