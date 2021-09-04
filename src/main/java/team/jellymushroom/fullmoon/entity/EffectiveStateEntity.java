package team.jellymushroom.fullmoon.entity;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.EffectiveFromEnum;

@Data
public class EffectiveStateEntity {

  private Integer index;

  private EffectiveFromEnum from;
}
