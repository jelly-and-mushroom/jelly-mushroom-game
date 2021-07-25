# 这是什么？
这是一个双人对战版的月圆之夜小游戏

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

# 启动VM参数
```
-Djava.awt.headless=false
```

# github 无法访问&慢 问题解决
[添加至/etc/hosts](https://raw.fastgit.org/521xueweihan/GitHub520/main/hosts)
[对应github库](https://github.com/521xueweihan/GitHub520)
若添加后未生效，再执行
```shell
sudo killall -HUP mDNSResponder
```
# TODO LIST
- 狼人出生自带状态未添加
- 卡牌
  - 所有卡牌生效规则均未添加

# 卡牌录入进度
- 女骑士
  - 攻击牌 普通攻击3