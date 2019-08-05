package com.dotterbear.task.scheduler.consumer.cassandra.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;

@Repository("task")
public interface TaskRepository extends CrudRepository<Task, UUID> {
}