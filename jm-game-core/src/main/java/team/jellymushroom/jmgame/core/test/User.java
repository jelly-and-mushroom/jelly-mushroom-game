package team.jellymushroom.jmgame.core.test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
public class User implements Serializable {

  private String username;

  private String password;

  public User() {
    log.info("创建新user实例");
  }
}
