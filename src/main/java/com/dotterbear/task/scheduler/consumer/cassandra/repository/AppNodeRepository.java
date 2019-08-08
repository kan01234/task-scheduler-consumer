package com.dotterbear.task.scheduler.consumer.cassandra.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.AppNode;

public interface AppNodeRepository extends CrudRepository<AppNode, UUID> {

}
