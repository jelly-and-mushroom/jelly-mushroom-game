package team.jellymushroom.fullmoon.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.constant.CardTypeEnum;
import team.jellymushroom.fullmoon.entity.AbilityEntity;
import team.jellymushroom.fullmoon.entity.game.BlessingEntity;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.card.*;
import team.jellymushroom.fullmoon.entity.http.HttpGameEntity;
import team.jellymushroom.fullmoon.entity.resource.ServiceResourceEntity;
import team.jellymushroom.fullmoon.util.CardUtil;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class ResourceService {

  @Value("${fm.resource.rootPath}")
  @Getter
  private String resourceRootPath;

  @Value("${fm.save.auto}")
  private Boolean autoSave;

  private String savePath = "/save/save.txt";

  @Getter
  private ServiceResourceEntity serviceResourceEntity = new ServiceResourceEntity();

  private static final String ENCODING = "UTF-8";

  private static final String JSON_ABILITYLIST_KEY = "abilityList";

  @PostConstruct
  public void init() {
    try {
      // 加载职业
      this.loadGameRole();
      // 加载卡牌
      this.loadCard();
      // 加载祝福
      this.loadBlessing();
    } catch (Exception e) {
      log.error("初始化server资源时出错，程序启动失败", e);
      System.exit(0);
    }
  }

  @SneakyThrows
  public void save(HttpGameEntity game) {
    if (!autoSave) {
      return;
    }
    Files.write(Paths.get(this.resourceRootPath + this.savePath), JSONObject.toJSONString(game).getBytes());
  }

  public HttpGameEntity load() {
    String dataStr = this.readFile(this.savePath);
    return JSONObject.parseObject(dataStr, HttpGameEntity.class);
  }

  private void loadGameRole() {
    String path = "/json/game_role.json";
    String dataStr = this.readFile(path);
    JSONArray gameRoleJSONArray = JSONArray.parseArray(dataStr);
    for (int i = 0; i < gameRoleJSONArray.size(); i++) {
      JSONObject gameRoleJSONObject = gameRoleJSONArray.getJSONObject(i);
      GameRoleEntity gameRoleEntity = JSONObject.parseObject(gameRoleJSONObject.toJSONString(), GameRoleEntity.class);
      this.serviceResourceEntity.getGameRoleMap().put(gameRoleEntity.getIndex(), gameRoleEntity);
    }
    log.info("游戏角色数据加载完成,path:{},size:{}", path, this.serviceResourceEntity.getGameRoleMap().size());
  }

  private void loadCard() {
    String path = "/json/game_card.json";
    String dataStr = this.readFile(path);
    JSONArray gameCardJSONArray = JSONArray.parseArray(dataStr);
    for (int i = 0; i < gameCardJSONArray.size(); i++) {
      JSONObject gameCardJSONObject = gameCardJSONArray.getJSONObject(i);
      CardTypeEnum cardType = CardTypeEnum.getEnumByIndex(gameCardJSONObject.getInteger("index"));
      CardEntity cardEntity = null;
      switch (cardType) {
        case ATTACK:
          cardEntity = JSONObject.parseObject(gameCardJSONObject.toJSONString(), AttackCardEntity.class);
          break;
        case ACTION:
          cardEntity = JSONObject.parseObject(gameCardJSONObject.toJSONString(), ActionCardEntity.class);
          break;
        case SPELL:
          cardEntity = JSONObject.parseObject(gameCardJSONObject.toJSONString(), SpellCardEntity.class);
          break;
        case MANA:
          cardEntity = JSONObject.parseObject(gameCardJSONObject.toJSONString(), ManaCardEntity.class);
          break;
        case COUNTER:
          cardEntity = JSONObject.parseObject(gameCardJSONObject.toJSONString(), CounterCardEntity.class);
          break;
        case EQUIPMENT:
          cardEntity = JSONObject.parseObject(gameCardJSONObject.toJSONString(), EquipmentCardEntity.class);
          break;
        case PRAYER:
          cardEntity = JSONObject.parseObject(gameCardJSONObject.toJSONString(), PrayerCardEntity.class);
          break;
        case SPECIAL:
          cardEntity = JSONObject.parseObject(gameCardJSONObject.toJSONString(), SpecialCardEntity.class);
      }
      setEffect(cardEntity.getAbilityList(), gameCardJSONObject.getJSONArray(ResourceService.JSON_ABILITYLIST_KEY));
      CardUtil.add(this.serviceResourceEntity.getCardList(), cardEntity);
      this.serviceResourceEntity.getCardMap().put(cardEntity.getIndex(), cardEntity);
    }
    log.info("游戏卡牌数据加载完成,path:{},size:{}", path, this.serviceResourceEntity.getCardList().size());
  }

  private void loadBlessing() {
    String path = "/json/game_blessing.json";
    String dataStr = this.readFile(path);
    JSONArray blessingJSONArray = JSONArray.parseArray(dataStr);
    for (int i = 0; i < blessingJSONArray.size(); i++) {
      JSONObject blessingJSONObject = blessingJSONArray.getJSONObject(i);
      BlessingEntity blessingEntity = JSONObject.parseObject(blessingJSONObject.toJSONString(), BlessingEntity.class);
      setEffect(blessingEntity.getAbilityList(), blessingJSONObject.getJSONArray(ResourceService.JSON_ABILITYLIST_KEY));
      this.serviceResourceEntity.getBlessingList().add(blessingEntity);
      this.serviceResourceEntity.getBlessingMap().put(blessingEntity.getIndex(), blessingEntity);
    }
    log.info("游戏祝福数据加载完成,path:{},size:{}", path, this.serviceResourceEntity.getBlessingMap().size());
  }

  @SneakyThrows
  private void setEffect(List<AbilityEntity> abilityList, JSONArray abilityArray) {
    for (int i = 0; i < abilityArray.size(); i++) {
      JSONObject abilityJson = abilityArray.getJSONObject(i);
      JSONObject effectJson = abilityJson.getJSONObject("effectData");
      AbilityEntity abilityEntity = abilityList.get(i);
      abilityEntity.setEffect(JSONObject.parseObject(effectJson.toJSONString(), (Type) Class.forName("team.jellymushroom.fullmoon.entity.effect." + effectJson.getString("type") + "EffectEntity")));
    }
  }

  @SneakyThrows
  private String readFile(String path) {
    File file = new File(this.resourceRootPath + path);
    return FileUtils.readFileToString(file, ENCODING);
  }
}
