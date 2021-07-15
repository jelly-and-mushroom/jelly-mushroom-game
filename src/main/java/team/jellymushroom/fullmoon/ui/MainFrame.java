package team.jellymushroom.fullmoon.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
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
import java.util.Map;
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
    // 获取角色资源
    Map<Integer, GameRoleEntity> gameRoleMap = this.uiService.getGameRoleMap();
    // 加载边框图片
    this.resource.setEdgingImg(ImageIO.read(new File(resourceRootPath + "/material/image/window.png")));
    // 加载角色图片
    for (Map.Entry<Integer, GameRoleEntity> roleEntry : gameRoleMap.entrySet()) {
      this.resource.getGameRoleImgMap().put(roleEntry.getKey(), ImageIO.read(new File(resourceRootPath + "/material/image/role/original/" + roleEntry.getKey() + ".png")));
    }
    // 加载虚化图片
    for (Map.Entry<Integer, GameRoleEntity> roleEntry : gameRoleMap.entrySet()) {
      this.resource.getGameDimRoleImgMap().put(roleEntry.getKey(), ImageIO.read(new File(resourceRootPath + "/material/image/role/dim/" + roleEntry.getKey() + ".png")));
    }
    // 加载确认图片
    this.resource.setConfirmImg(ImageIO.read(new File(resourceRootPath + "/material/image/confirm.png")));
    // 全部正常完成后打印日志
    log.info("ui resource 初始化完成,resourceRootPath:{}", resourceRootPath);
  }

  private void initUI() {
    // 窗口大小 4:3 固定不可配置
    super.setSize(1024, 768);
    // 窗口初始化位置
    super.setLocation(this.locationX, this.locationY);
    // 窗口标题
    Boolean isServer = this.uiService.getMainService().getIsServer();
    String serverMsg = "";
    if (null != isServer) {
      serverMsg = isServer ? "(服务端)" : "(客户端)";
    }
    super.setTitle("月圆之夜-双人对战联机版" + serverMsg);
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
    GameStageEnum stage = this.uiService.getMainService().getGameStage();
    if (null == stage) {
      return;
    }
    switch (stage) {
      case CHOOSE_ROLE:
      case CHOOSE_ROLE_DETAIL:
      case CHOOSE_ROLE_CONFIRM:
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
