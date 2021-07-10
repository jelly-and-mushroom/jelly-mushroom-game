package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

/**
 * 游戏阶段
 **/
@Getter
public enum GameStageEnum {

  CHOOSE_ROLE ("角色选择");

  /**
   * 阶段名称
   */
  private String name;


  GameStageEnum(String name) {
    this.name = name;
  }
}
