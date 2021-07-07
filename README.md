# 这是什么？
这是一个果冻与蘑菇共同制作的，双人联机小游戏(初步计划做一个双人对战版的月圆之夜)

# 项目结构
1. jm-game-core: 游戏核心代码，不打包为可执行jar，被jm-game-server及jm-game-client依赖使用，因此无法直接被spring容器管理，若要注入相应的bean，只能在server及client中注入
2. jm-game-server: 游戏服务端(可执行jar)
3. jm-game-client: 游戏客户端(可执行jar)

# sprintboot 启动速度慢
1. 终端输入查询主机名
```shell
reimuwang@B-V7XTQ05P-2253 ~ % hostname
B-V7XTQ05P-2253.local
```
2. 编辑 /etc/hosts (需要管理员权限)，将差得的主机名添加如其中
```
127.0.0.1	localhost B-V7XTQ05P-2253.local
::1             localhost B-V7XTQ05P-2253.local
```

# jm-game-server 启动VM参数
-Djava.rmi.server.hostname=192.168.31.51
其中，192.168.31.51 为server主机当前在局域网中的ip(也可以直接将/etc/hosts中localhost关联的127.0.0.1改为本ip，但这样改起来不方便，且可能导致其他服务出错)。保证同一个局域网的前提下，client需要连接的也是这个ip