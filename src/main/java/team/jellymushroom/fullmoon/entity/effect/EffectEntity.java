package team.jellymushroom.fullmoon.entity.effect;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Data
public abstract class EffectEntity {

  Boolean owner;

  public abstract void execute(PlayerEntity ownerPlayer, PlayerEntity opponentPlayer);
}
