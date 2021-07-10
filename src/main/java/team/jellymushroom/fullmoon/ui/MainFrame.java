package team.jellymushroom.fullmoon.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.jellymushroom.fullmoon.constant.GameRoleEnum;
import team.jellymushroom.fullmoon.entity.game.GameEntity;
import team.jellymushroom.fullmoon.entity.ui.UIResourceEntity;
import team.jellymushroom.fullmoon.keylistener.GameKeyListener;
import team.jellymushroom.fullmoon.service.UIService;
import team.jellymushroom.fullmoon.ui.module.ChooseRoleModule;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 游戏主面板
 */
@Component
@Slf4j
public class MainFrame extends Frame {

  @Value("${fm.ui.mainframe.location.x}")
  private Integer locationX;

  @Value("${fm.ui.mainframe.location.y}")
  private Integer locationY;

  @Value("${fm.ui.repaintInterval}")
  private Integer repaintInterval;

  private UIService uiService;

  private GameKeyListener gameKeyListener;

  private UIResourceEntity resource = new UIResourceEntity();

  public MainFrame(UIService uiService, GameKeyListener gameKeyListener) {
    this.uiService = uiService;
    this.gameKeyListener = gameKeyListener;
  }

  @PostConstruct
  public void init() throws IOException {
    this.initResource();
    this.initUI();
  }

  private void initResource() throws IOException {
    // 获取资源根目录
    String resourceRootPath = this.uiService.getResourceRootPath();
    // 加载边框图片
    this.resource.setEdgingImg(ImageIO.read(new File(resourceRootPath + "/material/image/window.png")));
    // 加载角色图片
    for (GameRoleEnum gameRoleEnum : GameRoleEnum.values()) {
      this.resource.getGameRoleImgMap().put(gameRoleEnum, ImageIO.read(new File(resourceRootPath + "/material/image/role/original/" + gameRoleEnum.getIndex() + ".png")));
    }
    // 加载虚化图片
    for (GameRoleEnum gameRoleEnum : GameRoleEnum.values()) {
      this.resource.getGameDimRoleImgMap().put(gameRoleEnum, ImageIO.read(new File(resourceRootPath + "/material/image/role/dim/" + gameRoleEnum.getIndex() + ".png")));
    }
    // 全部正常完成后打印日志
    log.info("ui resource 初始化完成,resourceRootPath:{}", resourceRootPath);
  }

  private void initUI() {
    // 窗口大小 4:3 固定不可配置
    super.setSize(1024, 768);
    // 窗口初始化位置
    super.setLocation(this.locationX, this.locationY);
    // 窗口标题
    super.setTitle("月圆之夜-双人对战联机版");
    // 关闭窗体时应用退出
    super.addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            log.info("关闭游戏主窗口，应用退出");
            System.exit(0);
          }
        }
    );
    // 绑定键盘监听
    super.addKeyListener(this.gameKeyListener);
    // 重绘定时任务
    this.startScheduleRepaint();
    // 设置窗体可见
    super.setVisible(true);
    // 全部正常完成后打印日志
    log.info("游戏主窗体初始化完成");
  }

  /**
   * 绘制组件
   */
  @Override
  public void paint(Graphics g) {
    GameEntity game = this.uiService.getGame();
    switch (game.getStage()) {
      case CHOOSE_ROLE:
        new ChooseRoleModule(this.uiService, this.resource, 0, 29, 1024, 739, 0).draw(g);
        break;
    }
  }

  /**
   * 通过双缓冲解决闪烁问题
   */
  @Override
  public void update(Graphics g) {
    Image bImage = super.createImage(this.getWidth(), this.getHeight());
    Graphics bg = bImage.getGraphics();
    this.paint(bg);
    bg.dispose();
    g.drawImage(bImage, 0, 0, this);
  }

  /**
   * 重绘定时任务
   */
  private void startScheduleRepaint() {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        MainFrame.this.repaint();
      }
    };
    ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
    ses.scheduleAtFixedRate(r, 0, this.repaintInterval, TimeUnit.MILLISECONDS);
  }
}
