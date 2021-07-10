package team.jellymushroom.fullmoon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.jellymushroom.fullmoon.entity.game.PlayerEntity;
import team.jellymushroom.fullmoon.entity.game.card.PrayerCardEntity;

@Controller
@Slf4j
public class TestController {

  @GetMapping("/test")
  @ResponseBody
  public PlayerEntity getArticleList() {
    log.info("收到test请求");
    PlayerEntity result = new PlayerEntity();
    PrayerCardEntity prayerCardEntity = new PrayerCardEntity();
    result.getPrayerCardPlaceList().add(prayerCardEntity);
    return result;
  }
}
