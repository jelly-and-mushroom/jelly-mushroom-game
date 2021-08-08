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
  private CardRecommendService cardRecommendService;

  public StageHandlerService(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService, CardRecommendService cardRecommendService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
    this.httpTransferService = httpTransferService;
    this.cardRecommendService = cardRecommendService;
  }
}
