package team.jellymushroom.fullmoon.entity.effect;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;

@Data
public class ModifyDeleteUncostEffectEntity extends EffectEntity {

  private Integer modifyValue;

  @Override
  public void execute(PlayerEntity ownerPlayer, PlayerEntity opponentPlayer) {
    PlayerEntity player = this.owner ? ownerPlayer : opponentPlayer;
    int wish = player.getDeleteUncostTimes() + this.modifyValue;
    if (wish < 0) {
      wish = 0;
    }
    player.setDeleteUncostTimes(wish);
  }
}
