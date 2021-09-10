package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.TimingEnum;
import team.jellymushroom.fullmoon.entity.AbilityEntity;
import team.jellymushroom.fullmoon.entity.game.EffectiveEntity;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Service
public class EffectService {

  @Getter
  private MainService mainService;

  public EffectService(MainService mainService) {
    this.mainService = mainService;
  }

  public void effect(PlayerEntity owner, PlayerEntity opponent, boolean timingOwner, TimingEnum timingEnum, EffectiveEntity effectiveEntity) {
    for (AbilityEntity ability : effectiveEntity.getAbilityList()) {
      if (ability.getTiming().getOwner() != timingOwner) {
        continue;
      }
      if (!ability.getTiming().getTiming().equals(timingEnum)) {
        continue;
      }
      ability.getEffect().execute(owner, opponent);
    }
  }
}
