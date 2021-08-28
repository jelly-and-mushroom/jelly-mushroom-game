package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import lombok.SneakyThrows;
import team.jellymushroom.fullmoon.constant.BlessingTypeEnum;
import team.jellymushroom.fullmoon.constant.CardGenreEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 祝福
 */
@Data
public class GameBlessingEntity implements Cloneable {

  private Integer index;

  private Boolean isValid;

  private String name;

  private List<Integer> roleIndexList = new ArrayList<>();

  private Boolean repeatable;

  private BlessingTypeEnum type;

  private List<CardGenreEnum> genreList = new ArrayList<>();

  @SneakyThrows
  public GameBlessingEntity copy() {
    return (GameBlessingEntity)this.clone();
  }
}
