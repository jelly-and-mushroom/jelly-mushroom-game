package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MainService {

  @Value("${fm.resource.rootPath}")
  @Getter
  private String resourceRootPath;
}
