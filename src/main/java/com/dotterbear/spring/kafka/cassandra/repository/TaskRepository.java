package com.dotterbear.spring.kafka.cassandra.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.dotterbear.spring.kafka.cassandra.entity.Task;

@Repository("task")
public interface TaskRepository extends CrudRepository<Task, UUID> {
}