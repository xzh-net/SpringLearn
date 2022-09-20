# Pulsar 2.10.1

## 1. function

## 2. connector


### 2.1 Flink从Pulsar中读取消息的操作

net.xzh.pulsar.connector.flink.FlinkFromPulsarSource

```bash
cd /opt/apache-pulsar-2.10.1/bin
./pulsar-admin topics create persistent://my-tenant/test-namespace/my-topic   # 创建一个没有分区的topic
./pulsar-client produce persistent://my-tenant/test-namespace/my-topic --messages "flink-pulsar"	# 生产消息
```

### 2.2 Flink将监听到数据导入Pulsar

net.xzh.pulsar.connector.flink.FlinkFromPulsarSink

```bash
yum install -y nc
nc -lk 44444	# node01打开监听端口

cd /opt/apache-pulsar-2.10.1/bin
./pulsar-client consume persistent://my-tenant/test-namespace/my-topic -s "first-sub"	# 启动监听者
```

### 2.3 基于Flink实现读取一个POJO类型的数据, 再数据写入到Pulsar中

net.xzh.pulsar.connector.flink.FlinkFromPulsarSchema

生产信息：net.xzh.pulsar.consumer.PulsarProducerSchemaTest

监听信息：net.xzh.pulsar.consumer.PulsarConsumerTest

如果消费失败可能是主题之前存在模型，需要重新创建主题

```bash
cd /opt/apache-pulsar-2.10.1/bin
./pulsar-admin topics create persistent://my-tenant/test-namespace/my-topic3
./pulsar-admin topics create persistent://my-tenant/test-namespace/my-topic4
```

