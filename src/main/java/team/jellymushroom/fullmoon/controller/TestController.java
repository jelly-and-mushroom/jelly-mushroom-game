package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jellymushroom.fullmoon.entity.game.card.CardEntity;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;

@RestController
@Slf4j
public class TestController {

  @GetMapping("/full-moon/test")
  public HttpResponseEntity test() {
    CardEntity card = new EquipmentCardEntity();
    System.out.println(JSONObject.toJSONString(card));
    EquipmentCardEntity equipmentCard = (EquipmentCardEntity)card;
    equipmentCard.setPlace(true);
    System.out.println(JSONObject.toJSONString(card));
    return HttpResponseEntity.success("success", null);
  }
}
