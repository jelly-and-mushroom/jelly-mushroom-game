package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import lombok.SneakyThrows;
import team.jellymushroom.fullmoon.constant.BlessingTypeEnum;

/**
 * 祝福
 */
@Data
public class BlessingEntity extends EffectiveEntity {

  private Boolean repeatable;

  private BlessingTypeEnum type;

  @SneakyThrows
  public BlessingEntity copy() {
    return (BlessingEntity)this.clone();
  }
}
