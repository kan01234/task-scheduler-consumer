package com.dotterbear.task.scheduler.consumer.cassandra.repository;

import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;

@Repository
public interface TaskRepository extends CassandraRepository<Task, UUID> {

}