package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HttpSignalEntity {

  private Integer index;

  private Integer index2;

  private List<Integer> cardIndexList = new ArrayList<>();

  private List<Integer> cardIndexList2 = new ArrayList<>();
}
