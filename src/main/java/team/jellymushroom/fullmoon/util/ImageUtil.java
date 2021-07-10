package team.jellymushroom.fullmoon.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

  private ImageUtil() {
  }

  /**
   * 虚化图片
   * @param inputPath 源文件路径
   * @param outputPath 虚化后输出的文件的路径
   * @param width 虚化间隔(越大像素点越少，图片越模糊，越暗)
   * @param outputFormat 输出图片格式(png, jpg)
   * @throws IOException
   */
  public static void dim(String inputPath, String outputPath, Integer width, String outputFormat) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(inputPath));
    boolean ifShowY = true;
    int nowY = 0;
    for (int y = 0; y < bufferedImage.getHeight(); y++) {
      if (!ifShowY) {
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
          bufferedImage.setRGB(x, y, 0);
        }
      } else {
        boolean ifShowX = true;
        int nowX = 0;
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
          if(!ifShowX) {
            bufferedImage.setRGB(x, y, 0);
          }
          nowX++;
          if (nowX == width) {
            nowX = 0;
            ifShowX = !ifShowX;
          }
        }
      }
      nowY++;
      if (nowY == width) {
        nowY = 0;
        ifShowY = !ifShowY;
      }
    }
    ImageIO.write(bufferedImage, outputFormat, new File(outputPath));
  }
}
