package team.jellymushroom.fullmoon.entity.game;

import lombok.Data;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class SignalEntity {

  private Integer index = 0;

  private Integer index2 = 0;

  private List<CardEntity> cardList = new ArrayList<>();

  private List<CardEntity> cardList2 = new ArrayList<>();

  private List<BlessingEntity> blessingList = new ArrayList<>();

  public void init() {
    this.index = 0;
    this.index2 = 0;
    this.cardList = new ArrayList<>();
    this.cardList2 = new ArrayList<>();
    this.blessingList = new ArrayList<>();
  }
}
