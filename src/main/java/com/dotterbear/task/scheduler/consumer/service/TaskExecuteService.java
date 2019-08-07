package com.dotterbear.task.scheduler.consumer.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;

@Service
public interface TaskExecuteService {

  public List<Task> markProcessing();

  public Task execute(Task task);
}
