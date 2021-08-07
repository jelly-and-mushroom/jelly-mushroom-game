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

  public StageHandlerService(MainService mainService, ResourceService resourceService, HttpTransferService httpTransferService) {
    this.mainService = mainService;
    this.resourceService = resourceService;
    this.httpTransferService = httpTransferService;
  }
}
