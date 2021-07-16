package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class UIService {

  @Getter
  private MainService mainService;

  @Getter
  private ResourceService resourceService;

  public UIService(MainService mainService, ResourceService resourceService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
  }
}
