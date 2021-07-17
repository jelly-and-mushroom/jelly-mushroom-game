package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

@Getter
public enum GameStageEnum {

  CHOOSE_ROLE ("角色选择"),
  CHOOSE_ROLE_DETAIL ("角色选择-详情"),
  CHOOSE_ROLE_CONFIRM("角色选择-已确定"),

  CHOOSE_CARD("游戏准备-选择卡牌");

  private String name;

  GameStageEnum(String name) {
    this.name = name;
  }
}
