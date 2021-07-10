package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameRoleEnum;

@Service
public class RoleChooseService {

  @Getter
  private Integer currentRoleIndex = 0;

  public void updateRoleIndex(int delta) {
    int preResult = this.currentRoleIndex + delta;
    if (preResult < 0) {
      this.currentRoleIndex = 0;
      return;
    }
    if (preResult >= GameRoleEnum.values().length) {
      this.currentRoleIndex  = GameRoleEnum.values().length - 1;
      return;
    }
    this.currentRoleIndex = preResult;
  }
}
