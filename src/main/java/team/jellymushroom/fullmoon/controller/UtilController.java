package team.jellymushroom.fullmoon.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import team.jellymushroom.fullmoon.entity.http.HttpResponseEntity;
import team.jellymushroom.fullmoon.service.UtilService;

/**
 * 工具，资源触发器
 */
@Controller
@Slf4j
public class UtilController {

  private UtilService utilService;

  public UtilController(UtilService utilService) {
    this.utilService = utilService;
  }

  @PostMapping("/full-moon/util/generateDimRoleImage")
  @ResponseBody
  public HttpResponseEntity generateDimRoleImage(@RequestBody JSONObject requestParam) {
    try {
      String logMark = "[" + System.currentTimeMillis() + "][http generateDimRoleImage]";
      log.info("{}收到http请求,requestParam:{}", logMark, requestParam);
      utilService.generateDimRoleImage(requestParam.getInteger("width"), logMark);
      log.info("{}虚化角色图片执行完成", logMark);
      return HttpResponseEntity.success(null, null);
    } catch (Exception e) {
      String errMsg = "虚化角色图片时出错";
      log.error(errMsg, e);
      return HttpResponseEntity.error(errMsg);
    }
  }
}
