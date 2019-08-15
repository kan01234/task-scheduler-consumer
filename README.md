# task-scheduler-consumer
Part of the implementation of task scheduler system, please check this [project](https://github.com/kan01234/task-scheduler) for more information.

## Quick Start
1. start Cassandra
```bash
cd ./cassandra-docker;
docker-compose up;
```
2. start Kafka
```bash
cd ./kafka-docker;
docker-compose up;
```
2.1 stop Kafka
do this step, if application can't find leader of partition
```bash
cd ./kafka-docker;
docker-compose rm -svf;
```
3. start application
```bash
docker-compose up;
```
