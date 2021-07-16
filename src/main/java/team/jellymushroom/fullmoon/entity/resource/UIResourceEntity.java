package team.jellymushroom.fullmoon.entity.resource;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * UI所需的静态资源
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
   * 角色图片map
   * key: gameRoleEntity.index
   */
  private Map<Integer, BufferedImage> gameRoleImgMap = new HashMap<>();

  /**
   * 虚化角色图片map
   * key: gameRoleEntity.index
   */
  private Map<Integer, BufferedImage> gameDimRoleImgMap = new HashMap<>();
}