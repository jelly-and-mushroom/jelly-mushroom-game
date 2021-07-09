package team.jellymushroom.jmgame.core.server.rmi;

/**
 * jm-game-client请求jm-game-server上报&获取数据的API(交互底层通过RMI实现)
 * 本接口在jm-game-server中实现
 *
 */
public interface IGameRMIService {

  String test(Integer testParam);
}
