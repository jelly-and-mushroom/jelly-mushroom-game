package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HttpSignalEntity {

  private Integer index;

  private Integer index2;

  private List<HttpCardEntity> cardList = new ArrayList<>();

  private List<HttpCardEntity> cardList2 = new ArrayList<>();

  private List<HttpGameBlessingEntity> blessingList = new ArrayList<>();
}
