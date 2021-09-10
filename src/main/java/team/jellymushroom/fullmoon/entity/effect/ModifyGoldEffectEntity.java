package team.jellymushroom.fullmoon.entity.effect;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Data
public class ModifyGoldEffectEntity extends EffectEntity {

  private Integer modifyValue;

  @Override
  public void execute(PlayerEntity ownerPlayer, PlayerEntity opponentPlayer) {
    PlayerEntity player = this.owner ? ownerPlayer : opponentPlayer;
    int wishGold = player.getGold() + this.modifyValue;
    if (wishGold < 0) {
      wishGold = 0;
    }
    player.setGold(wishGold);
  }
}
