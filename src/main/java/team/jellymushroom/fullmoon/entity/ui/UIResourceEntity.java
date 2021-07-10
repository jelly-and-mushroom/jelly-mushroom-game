package team.jellymushroom.fullmoon.entity.ui;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.GameRoleEnum;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * ui所需的一切静态资源
 */
@Data
public class UIResourceEntity {

  /**
   * 边框图片
   */
  private BufferedImage edgingImg;

  private Map<GameRoleEnum, BufferedImage> gameRoleImgMap = new HashMap<>(GameRoleEnum.values().length);
}
