package team.jellymushroom.fullmoon.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import team.jellymushroom.fullmoon.entity.game.GameRoleEntity;
import team.jellymushroom.fullmoon.entity.game.ServiceResourceEntity;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class ResourceService {

  private MainService mainService;

  @Getter
  private ServiceResourceEntity serviceResourceEntity = new ServiceResourceEntity();

  private static final String ENCODING = "UTF-8";

  public ResourceService(MainService mainService) {
    this.mainService = mainService;
  }

  @PostConstruct
  public void init() {
    try {
      // 加载职业
      this.loadGameRole();
    } catch (Exception e) {
      log.error("初始化server资源时出错，程序启动失败", e);
      System.exit(0);
    }
  }

  private void loadGameRole() throws IOException {
    String path = "/json/game_role.json";
    String dataStr = this.readFile(path);
    JSONArray gameRoleJSONArray = JSONArray.parseArray(dataStr);
    for (int i = 0; i < gameRoleJSONArray.size(); i++) {
      JSONObject gameRoleJSONObject = gameRoleJSONArray.getJSONObject(i);
      GameRoleEntity gameRoleEntity = JSONObject.parseObject(gameRoleJSONObject.toJSONString(), GameRoleEntity.class);
      this.serviceResourceEntity.getGameRoleMap().put(gameRoleEntity.getIndex(), gameRoleEntity);
    }
    log.info("游戏角色数据加载完成,path:{},value:{}", path, JSONObject.toJSONString(this.serviceResourceEntity.getGameRoleMap()));
  }

  private String readFile(String path) throws IOException {
    File file = new File(this.mainService.getResourceRootPath() + path);
    return FileUtils.readFileToString(file, ENCODING);
  }
}
