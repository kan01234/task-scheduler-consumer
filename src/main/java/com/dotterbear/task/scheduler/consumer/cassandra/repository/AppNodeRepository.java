package com.dotterbear.task.scheduler.consumer.cassandra.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.AppNode;

public interface AppNodeRepository extends CrudRepository<AppNode, UUID> {

  @Query(allowFiltering = true)
  List<AppNode> findByPingTsGreaterThanEqual(Long time);

  @Query(allowFiltering = true)
  List<AppNode> findByIsMaster(Boolean isMaster);

  @Query(allowFiltering = true)
  List<AppNode> findByIsMasterAndPingTsGreaterThanEqual(Boolean isMaster, Long time);

}
