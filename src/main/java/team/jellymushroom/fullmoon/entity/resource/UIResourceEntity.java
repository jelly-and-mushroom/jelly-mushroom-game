package team.jellymushroom.fullmoon.entity.resource;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * UI所需的静态资源
 */
@Data
public class UIResourceEntity {

  private static final Integer BLESSING_IN_ONE_IMG_COUNT = 7;

  public static Map<Integer, Integer> BLESSING_FIRST_Y_MAP = new HashMap<>();

  /**
   * 边框图片
   */
  private BufferedImage edgingImg;

  /**
   * 背景图片
   */
  private BufferedImage backgroundImg;

  /**
   * 电脑图标
   */
  private BufferedImage computerIconImg;

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

  /**
   * 卡牌图片map
   * key: cardEntity.index
   */
  private Map<Integer, BufferedImage> cardImgMap = new HashMap<>();

  /**
   * 祝福图片map
   * key: cardEntity.index
   */
  private Map<Integer, BufferedImage> blessingImgMap = new HashMap<>();

  static {
    UIResourceEntity.BLESSING_FIRST_Y_MAP.put(0, 283);
    UIResourceEntity.BLESSING_FIRST_Y_MAP.put(1, 291);
  }

  /**
   * @return
   * key: 祝福所在图片
   * value: 4个值，分别对应在图片中的 [左上角x, 左上角y, 右下角x, 右下角y]
   */
  @SuppressWarnings("unchecked")
  public Map.Entry<BufferedImage, List<Integer>> getBlessing(int index, boolean part) {
    int imgIndex = index / UIResourceEntity.BLESSING_IN_ONE_IMG_COUNT;
    List<Integer> coordinateList = new ArrayList<>(4);
    int location = index % UIResourceEntity.BLESSING_IN_ONE_IMG_COUNT;
    int interval = 224;
    if (part) {
      coordinateList.add(268);
      coordinateList.add(UIResourceEntity.BLESSING_FIRST_Y_MAP.get(imgIndex) + 5 + location * interval);
      coordinateList.add(455);
      coordinateList.add(UIResourceEntity.BLESSING_FIRST_Y_MAP.get(imgIndex) + 192 + location * interval);
    } else {
      coordinateList.add(224);
      coordinateList.add(UIResourceEntity.BLESSING_FIRST_Y_MAP.get(imgIndex) + location * interval);
      coordinateList.add(1315);
      coordinateList.add(UIResourceEntity.BLESSING_FIRST_Y_MAP.get(imgIndex) + 203 + location * interval);
    }
    return new AbstractMap.SimpleEntry(this.blessingImgMap.get(imgIndex), coordinateList);
  }
}
