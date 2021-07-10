package team.jellymushroom.fullmoon.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

  @PostConstruct
  public void init() {
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
    super.setVisible(true);
  }
}
