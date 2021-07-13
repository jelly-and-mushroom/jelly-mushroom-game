package team.jellymushroom.fullmoon.entity.ui;

import lombok.Data;

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

  /**
   * 确认图片
   */
  private BufferedImage confirmImg;

  /**
   * 角色图片
   */
  private Map<Integer, BufferedImage> gameRoleImgMap = new HashMap<>();

  /**
   * 虚化角色图片
   */
  private Map<Integer, BufferedImage> gameDimRoleImgMap = new HashMap<>();
}
