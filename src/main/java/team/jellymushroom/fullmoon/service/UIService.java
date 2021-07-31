package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class UIService {

  @Getter
  private MainService mainService;

  @Getter
  private ResourceService resourceService;

  @Getter
  private PrepareService prepareService;

  public UIService(MainService mainService, ResourceService resourceService, PrepareService prepareService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
    this.prepareService = prepareService;
  }
}
