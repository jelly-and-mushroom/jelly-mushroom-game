package team.jellymushroom.fullmoon.entity;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.TimingEnum;

@Data
public class TimingEntity {

  private Boolean mySelf;

  private TimingEnum timing;
}
