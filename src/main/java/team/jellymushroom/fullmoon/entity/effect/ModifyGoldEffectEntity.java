package team.jellymushroom.fullmoon.entity.effect;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Data
public class ModifyGoldEffectEntity extends EffectEntity {

  private Integer modifyValue;

  @Override
  public void execute(PlayerEntity ownerPlayer, PlayerEntity opponentPlayer) {
    PlayerEntity player = this.owner ? ownerPlayer : opponentPlayer;
    int wish = player.getGold() + this.modifyValue;
    if (wish < 0) {
      wish = 0;
    }
    player.setGold(wish);
  }
}
