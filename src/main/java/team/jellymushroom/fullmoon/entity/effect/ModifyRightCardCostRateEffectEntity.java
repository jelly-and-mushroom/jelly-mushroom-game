package team.jellymushroom.fullmoon.entity.effect;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Data
public class ModifyRightCardCostRateEffectEntity extends EffectEntity {

  private Double modifyValue;

  @Override
  public void execute(PlayerEntity ownerPlayer, PlayerEntity opponentPlayer) {
    PlayerEntity player = this.owner ? ownerPlayer : opponentPlayer;
    player.setRightCardCostRate(this.modifyValue);
  }
}
