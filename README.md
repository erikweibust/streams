# Streams - Kafka Streams POC

## Launching Services

### Need to run
1. Broker
2. Source topic
3. Sink topic
4. Producer - push data into Source Topic

### How to run
1. Broker with ZooKeeper
```
$ $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties

$ $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties
```

2. Source topic
```
$ $KAFKA_HOME/bin/kafka-topics.sh --create \
    --bootstrap-server localhost:9092 \
    --replication-factor 1 \
    --partitions 1 \
    --topic streams-plaintext-input
```

3. Sink topic
```
$ $KAFKA_HOME/bin/kafka-topics.sh --create \
    --bootstrap-server localhost:9092 \
    --replication-factor 1 \
    --partitions 1 \
    --topic streams-linesplit-output
    #--topic streams-pipe-output
```

4. Producer - push data into Source Topic
```
$ $KAFKA_HOME/bin/kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic streams-plaintext-input
```
