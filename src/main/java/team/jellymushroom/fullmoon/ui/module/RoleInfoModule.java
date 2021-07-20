package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
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
    // 判断当前游戏阶段是否在一小局游戏进行中
    boolean inGame = GameStageEnum.inGame(player.getStage());
    // hp
    int hp = inGame ? player.getGameInnerEntity().getHp() : player.getMaxHp();
    int maxHp = inGame ? player.getGameInnerEntity().getMaxHp() : player.getMaxHp();
    super.drawFillRect(g, slotX, slotY, slotWidth, slotHeight, Color.BLACK);
    super.drawFillRect(g, slotX, slotY, (int)(slotWidth * 1.0 * (hp / maxHp)), slotHeight, Color.PINK);
    super.drawFont(g, fontX, fontY + slotHeight, hp+ "/" + maxHp, Color.WHITE, Font.PLAIN, fontSize);
    // mp
    int mp = inGame ? player.getGameInnerEntity().getMp() : player.getInitMp();
    double mpRate = mp==0 ? 0.0 : 0.5;
    super.drawFillRect(g, slotX, slotY + slotHeight, slotWidth, slotHeight, Color.BLACK);
    super.drawFillRect(g, slotX, slotY + slotHeight, (int)(slotWidth * mpRate), slotHeight, Color.CYAN);
    super.drawFont(g, fontX, fontY + 2 * slotHeight, mp + "", Color.WHITE, Font.PLAIN, fontSize);
    // 行动力
    int action = inGame ? player.getGameInnerEntity().getAction() : player.getMaxAction();
    int maxAction = inGame ? player.getGameInnerEntity().getMaxAction() : player.getMaxAction();
    double actionRate = 1.0 * action / maxAction;
    if (action > maxAction) {
      actionRate = 1.0;
    }
    super.drawFillRect(g, slotX, slotY + 2 * slotHeight, slotWidth, slotHeight, Color.BLACK);
    super.drawFillRect(g, slotX, slotY + 2 * slotHeight, (int)(slotWidth * actionRate), slotHeight, Color.ORANGE);
    super.drawFont(g, fontX, fontY + 3 * slotHeight, action + "/" + maxAction, Color.WHITE, Font.PLAIN, fontSize);
    // 金币
    super.drawFillRect(g, slotX, slotY + 3 * slotHeight, slotWidth, slotHeight, Color.BLACK);
    super.drawFont(g, fontX, fontY + 4 * slotHeight, "金币:" + player.getGold(), Color.WHITE, Font.PLAIN, fontSize);
    // 绘制边框
    super.drawWindow(g);
  }
}
