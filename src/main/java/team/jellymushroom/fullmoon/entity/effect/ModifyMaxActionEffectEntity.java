package team.jellymushroom.fullmoon.entity.effect;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Data
public class ModifyMaxActionEffectEntity extends EffectEntity {

  private Integer modifyValue;

  private Boolean innerGame;

  @Override
  public void execute(PlayerEntity ownerPlayer, PlayerEntity opponentPlayer) {
    PlayerEntity player = this.owner ? ownerPlayer : opponentPlayer;
    if (this.innerGame) {
      int wish = player.getGameInnerEntity().getMaxAction() + this.modifyValue;
      if (wish < 0) {
        wish = 0;
      }
      player.getGameInnerEntity().setMaxAction(wish);
      return;
    }
    int wish = player.getMaxAction() + this.modifyValue;
    if (wish < 0) {
      wish = 0;
    }
    player.setMaxAction(wish);
  }
}
