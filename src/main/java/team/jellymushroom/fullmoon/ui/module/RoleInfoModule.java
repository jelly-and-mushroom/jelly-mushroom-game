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
    int fontSize = this.iHeight / 4;
    int xAdd = 50;
    // hp
    super.drawFillRect(g, this.iX, this.iY, this.iWidth, fontSize, Color.BLACK);
    super.drawFillRect(g, this.iX, this.iY, this.iWidth, fontSize, Color.RED);
    super.drawFont(g, this.iX + xAdd, this.iY + fontSize, player.getMaxHp() + "/" + player.getMaxHp(), Color.GREEN, Font.PLAIN, fontSize);
    // mp
    super.drawFillRect(g, this.iX, this.iY + fontSize, this.iWidth, fontSize, Color.BLACK);
    super.drawFillRect(g, this.iX, this.iY + fontSize, this.iWidth, fontSize, Color.BLUE);
    super.drawFont(g, this.iX + xAdd, this.iY + 2 * fontSize, player.getInitMp() + "", Color.GREEN, Font.PLAIN, fontSize);
    // 行动力
    super.drawFillRect(g, this.iX, this.iY + 2 * fontSize, this.iWidth, fontSize, Color.BLACK);
    super.drawFillRect(g, this.iX, this.iY + 2 * fontSize, this.iWidth, fontSize, Color.YELLOW);
    super.drawFont(g, this.iX + xAdd, this.iY + 3 * fontSize, player.getMaxAction() + "", Color.GREEN, Font.PLAIN, fontSize);
    // 金币
    super.drawFillRect(g, this.iX, this.iY + 3 * fontSize, this.iWidth, fontSize, Color.BLACK);
    super.drawFont(g, this.iX + xAdd, this.iY + 4 * fontSize, "金币:" + player.getGold(), Color.GREEN, Font.PLAIN, fontSize);
    // 绘制边框
    super.drawWindow(g);
  }
}
