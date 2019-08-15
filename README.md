# task-scheduler-consumer
Part of the implementation of task scheduler system, please check this [project](https://github.com/kan01234/task-scheduler) for more information.

## Quick Start
1. create docker network
```bash
docker network create task-scheduler-network;
```

2. start Cassandra
```bash
cd ./cassandra-docker;
docker-compose up;
```

3. start Kafka
```bash
export DOCKER_KAFKA_HOST=$(ipconfig getifaddr en0);
cd ./kafka-docker;
docker-compose rm -svf; docker-compose up;
```

3. start application
```bash
mvn clean package;
docker-compose up;
```
