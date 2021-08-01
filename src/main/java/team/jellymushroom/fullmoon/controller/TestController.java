package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jellymushroom.fullmoon.constant.CardLevelEnum;
import team.jellymushroom.fullmoon.entity.game.card.EquipmentCardEntity;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;

@RestController
@Slf4j
public class TestController {

  @GetMapping("/full-moon/test")
  public HttpResponseEntity test() {
    EquipmentCardEntity card = new EquipmentCardEntity();
    System.out.println(JSONObject.toJSONString(card));
    card.setLevel(CardLevelEnum.LEVEL_S);
    System.out.println(JSONObject.toJSONString(card));
    card.setLevel(CardLevelEnum.LEVEL_A);
    System.out.println(JSONObject.toJSONString(card));
    return HttpResponseEntity.success("success", null);
  }
}
