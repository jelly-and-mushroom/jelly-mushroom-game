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
      this.drawPrepare(g, roleWidth);
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

  private void drawPrepare(Graphics g, int roleWidth) {
    // 选项模块高
    int optionHeight = 80;
    // 选项模块一行整体的宽度
    int optionLineWidth = 660;
    // 第一行x坐标
    int firstLineX = this.iX + (this.iWidth - roleWidth - optionLineWidth) / 2;
    // 第一行y坐标
    int firstLineY = this.iY + (this.iHeight - 5 * optionHeight) / 2;
    // 第一行模块
    new PrepareOptionModule(this.uiService, this.resource, firstLineX, firstLineY, optionLineWidth / 2, optionHeight, 0).draw(g);
    new PrepareOptionModule(this.uiService, this.resource, firstLineX + optionLineWidth / 2, firstLineY, optionLineWidth / 2, optionHeight, 0).draw(g);
    // 第二行模块
    new PrepareOptionModule(this.uiService, this.resource, firstLineX, firstLineY + optionHeight, optionLineWidth / 2, optionHeight, 0).draw(g);
    new PrepareOptionModule(this.uiService, this.resource, firstLineX + optionLineWidth / 2, firstLineY + optionHeight, optionLineWidth / 2, optionHeight, 0).draw(g);
    // 第三行模块
    new PrepareOptionModule(this.uiService, this.resource, firstLineX, firstLineY + 2 * optionHeight, optionLineWidth / 2, optionHeight, 0).draw(g);
    new PrepareOptionModule(this.uiService, this.resource, firstLineX + optionLineWidth / 2, firstLineY + 2 * optionHeight, optionLineWidth / 2, optionHeight, 0).draw(g);
    // 第四行模块
    new PrepareOptionModule(this.uiService, this.resource, firstLineX, firstLineY + 3 * optionHeight, optionLineWidth / 3, optionHeight, 0).draw(g);
    new PrepareOptionModule(this.uiService, this.resource, firstLineX + optionLineWidth / 3, firstLineY + 3 * optionHeight, optionLineWidth / 3, optionHeight, 0).draw(g);
    new PrepareOptionModule(this.uiService, this.resource, firstLineX + optionLineWidth * 2 / 3, firstLineY + 3 * optionHeight, optionLineWidth / 3, optionHeight, 0).draw(g);
    // 第五行模块
    new PrepareOptionModule(this.uiService, this.resource, firstLineX, firstLineY + 4 * optionHeight, optionLineWidth / 3, optionHeight, 0).draw(g);
    new PrepareOptionModule(this.uiService, this.resource, firstLineX + optionLineWidth / 3, firstLineY + 4 * optionHeight, optionLineWidth / 3, optionHeight, 0).draw(g);
    new PrepareOptionModule(this.uiService, this.resource, firstLineX + optionLineWidth * 2 / 3, firstLineY + 4 * optionHeight, optionLineWidth / 3, optionHeight, 0).draw(g);
  }

  private void drawInGame(Graphics g, int roleWidth, int roleHeight, int roleInfoGHeight) {
    // 绘制对手
    new MainFrameRoleModule(this.uiService, this.resource, this.iX, this.iY, roleWidth, roleHeight, 0, false).draw(g);
    new RoleInfoModule(this.uiService, this.resource, this.iX, this.iY + roleHeight, roleWidth, roleInfoGHeight, 0, false).draw(g);
  }
}
