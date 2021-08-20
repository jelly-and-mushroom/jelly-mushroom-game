package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

/**
 * 等待连接阶段需通过http传递的信息
 */
@Data
public class HttpWaitConnectEntity {

  private Long serverPriority;
}
