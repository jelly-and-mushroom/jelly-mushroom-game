package team.jellymushroom.jmgame.server.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.jmgame.core.test.IUserService;
import team.jellymushroom.jmgame.core.test.User;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

  @Override
  public User getUserByName(String username) {
    User user = null;
    if (username != null && !username.equals("")) {
      user = new User();
      if (username.equals("admin")) {
        user.setUsername("admin");
        user.setPassword("123456");
      }else{
        user.setUsername("xxxx");
        user.setPassword("111111");
      }

    }
    log.info(user.toString());
    return user;
  }
}
