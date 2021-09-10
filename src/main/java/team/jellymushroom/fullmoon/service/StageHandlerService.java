package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class StageHandlerService {

  @Getter
  private MainService mainService;

  @Getter
  private ResourceService resourceService;

  @Getter
  private HttpTransferService httpTransferService;

  @Getter
  private RecommendService recommendService;

  @Getter
  private EffectService effectService;

  public StageHandlerService(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, RecommendService recommendService, EffectService effectService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
    this.httpTransferService = httpTransferService;
    this.recommendService = recommendService;
    this.effectService = effectService;
  }
}
