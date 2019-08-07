package com.dotterbear.task.scheduler.consumer.cassandra.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {

  @Query(allowFiltering = true)
  List<Task> findByStateAndExecTsLessThanEqual(final String state, final Date execTs);

}
