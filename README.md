# 这是什么？
这是一个双人对战模式版的月圆之夜小游戏

# sprintboot 启动速度慢
1. 查询主机名
```shell
reimuwang@B-V7XTQ05P-2253 ~ % hostname
B-V7XTQ05P-2253.local
```
2. 编辑 /etc/hosts (需要管理员权限)，加入上文查得的主机名
```
127.0.0.1	localhost B-V7XTQ05P-2253.local
::1             localhost B-V7XTQ05P-2253.local
```