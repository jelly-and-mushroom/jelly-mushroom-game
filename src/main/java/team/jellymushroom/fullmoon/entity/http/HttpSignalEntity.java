package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HttpSignalEntity {

  private Integer index;

  private List<Integer> cardIndexList = new ArrayList<>();

  private Integer prepareOptionIndex;
}
