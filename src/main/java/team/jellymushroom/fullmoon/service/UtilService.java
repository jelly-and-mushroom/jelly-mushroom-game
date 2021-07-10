package team.jellymushroom.fullmoon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.GameRoleEnum;
import team.jellymushroom.fullmoon.util.ImageUtil;

import java.io.IOException;

@Service
@Slf4j
public class UtilService {

  private MainService mainService;

  public UtilService(MainService mainService) {
    this.mainService = mainService;
  }

  public void generateDimRoleImage(Integer width, String logMark) throws IOException {
    String inputDir = this.mainService.getResourceRootPath() + "/material/image/role/original/";
    String outputDir = this.mainService.getResourceRootPath() + "/material/image/role/dim/";
    for (GameRoleEnum gameRoleEnum : GameRoleEnum.values()) {
      String format = "png";
      String fileName = gameRoleEnum.getIndex() + "." + format;
      ImageUtil.dim(inputDir + fileName, outputDir + fileName, width, format);
      log.info("{}虚化图片成功,inputDir:{},outputDir:{},fileName:{},width:{}", logMark, inputDir, outputDir, fileName, width);
    }
  }
}
