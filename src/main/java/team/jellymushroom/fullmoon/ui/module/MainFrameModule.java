package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.service.UIService;

import java.awt.*;

public class MainFrameModule extends Module {

  public MainFrameModule(UIService uiService, UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(uiService, resource, oX, oY, oWidth, oHeight, padding);
  }

  @Override
  public void draw(Graphics g) {
    // 角色图片尺寸
    int roleWidth = 225;
    int roleHeight = (int)(1.0 * this.iHeight / 3);
    int roleInfoGHeight = roleHeight / 2 + 2;
    // 基础信息
    this.drawBase(g, roleWidth, roleHeight, roleInfoGHeight);
    // 绘制其他信息
    boolean inGame = GameStageEnum.inGame(this.uiService.getMainService().getPlayerMyself().getStage());
    if (inGame) {
      this.drawInGame(g, roleWidth, roleHeight, roleInfoGHeight);
    } else {
      this.drawPrepare(g);
    }
  }

  private void drawBase(Graphics g, int roleWidth, int roleHeight, int roleInfoGHeight) {
    // 绘制背景图片
    super.drawBackgroundImg(g);
    // 绘制自身
    new MainFrameRoleModule(this.uiService, this.resource, this.iX + this.iWidth - roleWidth, this.iY + this.iHeight / 2, roleWidth, roleHeight, 0, true).draw(g);
    new RoleInfoModule(this.uiService, this.resource, this.iX + this.iWidth - roleWidth, this.iY + this.iHeight / 2 + roleHeight, roleWidth, roleInfoGHeight, 0, true).draw(g);
    // 绘制边框
    super.drawWindow(g);
  }

  private void drawPrepare(Graphics g) {
  }

  private void drawInGame(Graphics g, int roleWidth, int roleHeight, int roleInfoGHeight) {
    // 绘制对手
    new MainFrameRoleModule(this.uiService, this.resource, this.iX, this.iY, roleWidth, roleHeight, 0, false).draw(g);
    new RoleInfoModule(this.uiService, this.resource, this.iX, this.iY + roleHeight, roleWidth, roleInfoGHeight, 0, false).draw(g);
  }
}
