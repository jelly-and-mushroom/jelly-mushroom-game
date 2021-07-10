package team.jellymushroom.fullmoon.keylistener;

import org.springframework.stereotype.Component;
import team.jellymushroom.fullmoon.service.KeyEventService;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
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
    keyEventService.keyPressed(e);
  }

  /**
   * 松开
   */
  @Override
  public void keyReleased(KeyEvent e) {
  }
}
