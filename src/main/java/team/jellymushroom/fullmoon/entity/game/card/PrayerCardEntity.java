package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;

/**
 * 祷告牌
 */
@Data
public class PrayerCardEntity extends CardEntity {

  /**
   * 只有已放置入场上时(即在playerEntity.prayerCardPlaceList中)，本参数才有意义
   */
  private Integer remaininPrayerCount;
}
