# docker 安装rocketmq

## **1. 安装 Namesrv**

```
docker pull rocketmqinc/rocketmq:latest
```

**启动容器**

```
docker run -d -p 9876:9876 -v /data/rocketmq/logs:/root/logs -v /data/rocketmq/store:/root/store --name rmqnamesrv -e "MAX_POSSIBLE_HEAP=100000000" docker.io/rocketmqinc/rocketmq:latest sh mqnamesrv
```



## **2.安装 broker 服务器**

与上步是同一个镜像，如果上步完成，此步无需拉取

**创建 broker.conf 文件**

1. 在 /data/rocketmq/conf 目录下创建 broker.conf 文件
2. 在 broker.conf 中写入如下内容

```
brokerClusterName = DefaultCluster
brokerName = broker-a brokerId = 0 
deleteWhen = 04 
fileReservedTime = 48 
brokerRole = ASYNC_MASTER 
flushDiskType = ASYNC_FLUSH
brokerIP1 = {本地外网 IP} 
autoCreateTopicEnable=true
```

**brokerIP1 要修改成你自己宿主机的 IP**

**启动容器**

```
docker run -d -p 10911:10911 -p 10909:10909 -v  /data/rocketmq/logs:/root/logs -v  /data/rocketmq/store:/root/store -v  /data/rocketmq/conf/broker.conf:/opt/rocketmq-latest/conf/broker.conf --name rmqbroker --link rmqnamesrv:namesrv -e "NAMESRV_ADDR=namesrv:9876" -e "MAX_POSSIBLE_HEAP=200000000" rocketmqinc/rocketmq:latest sh mqbroker -c /opt/rocketmq-latest/conf/broker.conf
```



## **3. 安装 rocketmq 控制台**

**拉取镜像**

```
docker pull pangliang/rocketmq-console-ng
```

**启动容器**

```
docker run -e "JAVA_OPTS=-Drocketmq.namesrv.addr={本地外网 IP}:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" -p 8080:8080 -t pangliang/rocketmq-console-ng
```

访问地址:http://121.199.21.87:8080/()