package team.jellymushroom.fullmoon.ui.module;

import lombok.extern.slf4j.Slf4j;
import team.jellymushroom.fullmoon.constant.GameRoleEnum;
import team.jellymushroom.fullmoon.entity.ui.UIResourceEntity;

import java.awt.*;

/**
 * 角色选择模块
 *
 * 初始化inner信息:
 * iWidth:1010,iHeight:725
 */
@Slf4j
public class ChooseRoleModule extends Module {

  public ChooseRoleModule(UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
    super(resource, oX, oY, oWidth, oHeight, padding);
    log.info("ChooseRoleModule iWidth:{},iHeight:{}", this.iWidth, this.iHeight);
  }

  @Override
  public void draw(Graphics g) {
    int moduleRoleOWidth = this.iWidth / 9;
    int moduleRoleOHeight = this.iHeight / 2;
    int adjustX = 1;
    int addX = adjustX;
    // 对手选职业
    for (GameRoleEnum gameRoleEnum : GameRoleEnum.values()) {
      new RoleModule(this.resource, this.iX + addX, this.iY, moduleRoleOWidth, moduleRoleOHeight, 0, gameRoleEnum).draw(g);
      addX += moduleRoleOWidth;
    }
    addX = adjustX;
    // 自身选职业
    for (GameRoleEnum gameRoleEnum : GameRoleEnum.values()) {
      new RoleModule(this.resource, this.iX + addX, this.iY + moduleRoleOHeight, moduleRoleOWidth, moduleRoleOHeight, 0, gameRoleEnum).draw(g);
      addX += moduleRoleOWidth;
    }
    super.drawWindow(g);
  }
}
