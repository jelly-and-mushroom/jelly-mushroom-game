package team.jellymushroom.fullmoon.keylistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import team.jellymushroom.fullmoon.service.KeyEventService;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
@Slf4j
public class GameKeyListener implements KeyListener {

  private KeyEventService keyEventService;

  public GameKeyListener(KeyEventService keyEventService) {
    this.keyEventService = keyEventService;
  }

  /**
   * 输入
   */
  @Override
  public void keyTyped(KeyEvent e) {
  }

  /**
   * 按下
   */
  @Override
  public void keyPressed(KeyEvent e) {
    log.info("本地按下按键,keyCode:{}", e.getKeyCode());
    keyEventService.keyPressed(e.getKeyCode(), true);
  }

  /**
   * 松开
   */
  @Override
  public void keyReleased(KeyEvent e) {
  }
}
