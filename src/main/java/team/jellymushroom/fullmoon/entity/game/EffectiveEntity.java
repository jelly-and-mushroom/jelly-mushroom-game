package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.GenreEnum;
import team.jellymushroom.fullmoon.entity.AbilityEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class EffectiveEntity implements Cloneable {

  protected Integer index;

  protected Boolean isValid;

  protected String name;

  protected List<Integer> roleIndexList = new ArrayList<>();

  protected List<GenreEnum> genreList = new ArrayList<>();

  protected List<AbilityEntity> abilityList = new ArrayList<>();

  public boolean getatable(int roleIndex) {
    for (Integer roleGetatable : this.roleIndexList) {
      if (roleGetatable == roleIndex) {
        return true;
      }
    }
    return false;
  }
}
