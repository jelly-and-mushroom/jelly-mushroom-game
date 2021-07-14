package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;

/**
 * 祷告牌
 */
@Data
public class PrayerCardEntity extends CardEntity {

  /**
   * 剩余祷告回合数
   * 只有当本祷告牌被放置到场上后，本字段才有意义
   */
  private Integer remainPrayerCount;
}
