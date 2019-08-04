package com.dotterbear.spring.kafka.cassandra.repository;

import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import com.dotterbear.spring.kafka.cassandra.entity.Task;

@Repository
public interface TaskRepository extends CassandraRepository<Task, UUID> {
}