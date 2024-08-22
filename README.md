# Streams - Kafka Streams POC

## Launching Services

### Need to run
1. Broker
2. Source topic
3. Sink topic
4. Producer - push data into Source Topic

### How to run
1. Broker with ZooKeeper or KRaft

Note: could not get this method to work consistently. Going with KRaft route (see below)

#### Starting Kafta w/ Zookeeper
```
$ $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties

$ $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties
```
#### Starting Kafta w/ KRaft
Generate a Cluser UUID (Do this one time)
```
$ KAFKA_CLUSTER_ID="$($KAFKA_HOME/bin/kafka-storage.sh random-uuid)"
```
Format Log Directories
```
$ $KAFKA_HOME/bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID \
    -c $KAFKA_HOME/config/kraft/server.properties
```
Start the Kafka server
```
$ $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/kraft/server.properties
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
