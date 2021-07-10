package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameRoleEnum;

@Service
public class RoleChooseService {

  private MainService mainService;

  public RoleChooseService(MainService mainService) {
    this.mainService = mainService;
  }

  @Getter
  private GameRoleEnum currentRole = GameRoleEnum.getEnumByValue(0);

  @Getter
  @Setter
  private Boolean showDetail = false;

  public void updateRole(int delta) {
    if (null != this.mainService.getGameEntity().getMySelf().getGameRoleEnum()) {
      return;
    }
    int preResult = this.currentRole.getIndex() + delta;
    if (preResult < 0) {
      this.currentRole = GameRoleEnum.getEnumByValue(GameRoleEnum.values().length - 1);
      return;
    }
    if (preResult >= GameRoleEnum.values().length) {
      this.currentRole  = GameRoleEnum.getEnumByValue(0);
      return;
    }
    this.currentRole  = GameRoleEnum.getEnumByValue(preResult);
  }

  public void confirm() {
    mainService.getGameEntity().getMySelf().setGameRoleEnum(currentRole);
  }

  public void cancelConfirm() {
    mainService.getGameEntity().getMySelf().setGameRoleEnum(null);
  }
}
