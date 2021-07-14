package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

@Getter
public enum GameStageEnum {

  CHOOSE_ROLE ("角色选择"),
  CHOOSE_ROLE_DETAIL ("角色选择-详情"),
  CHOOSE_ROLE_CONFIRM("角色选择-已确认");

  private String name;

  GameStageEnum(String name) {
    this.name = name;
  }
}
