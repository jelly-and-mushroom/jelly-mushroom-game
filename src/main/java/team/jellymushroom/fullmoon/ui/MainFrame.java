package team.jellymushroom.fullmoon.ui;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.jellymushroom.fullmoon.constant.GameStageEnum;
import team.jellymushroom.fullmoon.entity.control.ServerControlEntity;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.resource.UIResourceEntity;
import team.jellymushroom.fullmoon.keylistener.GameKeyListener;
import team.jellymushroom.fullmoon.service.UIService;
import team.jellymushroom.fullmoon.ui.module.CardModule;
import team.jellymushroom.fullmoon.ui.module.ChooseRoleModule;
import team.jellymushroom.fullmoon.ui.module.MainFrameModule;
import team.jellymushroom.fullmoon.ui.module.WaitConnectModule;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
  public void init() {
    this.initResource();
    this.initUI();
  }

  @SneakyThrows
  private void initResource() {
    // 获取资源根目录
    String resourceRootPath = this.uiService.getResourceService().getResourceRootPath();
    // 加载边框图片
    this.resource.setEdgingImg(ImageIO.read(new File(resourceRootPath + "/material/image/window.png")));
    // 加载背景图片
    this.resource.setBackgroundImg(ImageIO.read(new File(resourceRootPath + "/material/image/background.png")));
    // 加载电脑图标
    this.resource.setComputerIconImg(ImageIO.read(new File(resourceRootPath + "/material/image/computer_icon.jpg")));
    // 加载角色图片
    Map<Integer, GameRoleEntity> gameRoleMap = this.uiService.getResourceService().getServiceResourceEntity().getGameRoleMap();
    for (Map.Entry<Integer, GameRoleEntity> roleEntry : gameRoleMap.entrySet()) {
      this.resource.getGameRoleImgMap().put(roleEntry.getKey(), ImageIO.read(new File(resourceRootPath + "/material/image/role/original/" + roleEntry.getKey() + ".png")));
      this.resource.getGameDimRoleImgMap().put(roleEntry.getKey(), ImageIO.read(new File(resourceRootPath + "/material/image/role/dim/" + roleEntry.getKey() + ".png")));
    }
    // 加载卡牌图片
    Map<Integer, CardEntity> cardMap = this.uiService.getResourceService().getServiceResourceEntity().getCardMap();
    for (Map.Entry<Integer, CardEntity> cardEntry : cardMap.entrySet()) {
      this.resource.getCardImgMap().put(cardEntry.getKey(), ImageIO.read(new File(resourceRootPath + "/material/image/card/" + cardEntry.getKey() + ".png")));
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

  @Override
  public void paint(Graphics g) {
    Boolean isServer = ServerControlEntity.getInstance().getIsServer();
    int oY = 29;
    int oWidth = 1024;
    int oHeight = 739;
    if (null == isServer) {
      new WaitConnectModule(this.uiService, this.resource, 0, oY, oWidth, oHeight, 0).draw(g);
      return;
    }
    GameStageEnum gameStage = this.uiService.getMainService().getPlayerMyself().getStage();
    switch (gameStage) {
      case CHOOSE_ROLE:
      case CHOOSE_ROLE_DETAIL:
      case CHOOSE_ROLE_CONFIRM:
        new ChooseRoleModule(this.uiService, this.resource, 0, oY, oWidth, oHeight, 0).draw(g);
        break;
      case PREPARE:
      case PREPARE_MY_CARD_REPOSITORY:
      case PREPARE_MY_CARD_REPOSITORY_DETAIL:
        new MainFrameModule(this.uiService, this.resource, 0, oY, oWidth, oHeight, 0).draw(g);
    }
  }

  @Override
  public void update(Graphics g) {
    Image bImage = super.createImage(this.getWidth(), this.getHeight());
    Graphics bg = bImage.getGraphics();
    this.paint(bg);
    bg.dispose();
    g.drawImage(bImage, 0, 0, this);
  }

  private void startScheduleRepaint() {
    Runnable r = () -> MainFrame.this.repaint();
    ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
    ses.scheduleAtFixedRate(r, 0, this.repaintInterval, TimeUnit.MILLISECONDS);
  }
}
