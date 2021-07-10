package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class RoleChooseService {

  @Getter
  private Integer currentRoleIndex = 0;
}
