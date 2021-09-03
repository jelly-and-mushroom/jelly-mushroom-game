package team.jellymushroom.fullmoon.entity;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.effect.EffectEntity;

@Data
public class AbilityEntity {

  private TimingEntity timing;

  private EffectEntity effect;
}
