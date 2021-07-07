# 这是什么？
这是一个果冻与蘑菇共同制作的，双人联机小游戏

# 项目结构
1. jm-game-core: 游戏核心代码，不打包为可执行jar，被jm-game-server及jm-game-client依赖使用，因此无法直接被spring容器管理，若要注入相应的bean，只能在server及client中注入
2. jm-game-server: 游戏服务端(可执行jar)
3. jm-game-client: 游戏客户端(可执行jar)