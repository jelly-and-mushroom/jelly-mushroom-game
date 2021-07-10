package team.jellymushroom.fullmoon.service;

import org.springframework.stereotype.Service;

/**
 * 与ui进行交互的service
 */
@Service
public class UIService {

  private MainService mainService;

  public UIService(MainService mainService) {
    this.mainService = mainService;
  }

  /**
   * 获得资源根目录
   */
  public String getResourceRootPath() {
    return this.mainService.getResourceRootPath();
  }
}
