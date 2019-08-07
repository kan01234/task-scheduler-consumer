package com.dotterbear.task.scheduler.consumer.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;
import com.dotterbear.task.scheduler.consumer.cassandra.repository.TaskRepository;

@Service
public class TaskExecuteServiceImpl implements TaskExecuteService {

  private static final Logger logger = LoggerFactory.getLogger(TaskExecuteServiceImpl.class);

  @Autowired
  private TaskRepository taskRepository;

  @Override
  public List<Task> markProcessing() {
    List<Task> tasks = taskRepository.findByStateAndExecTsLessThanEqual(Task.INIT, new Date());
    taskRepository.saveAll(
        tasks.stream().map(task -> task.setState(Task.PROCESSING)).collect(Collectors.toList()));
    return tasks;
  }

  @Override
  public Task execute(Task task) {
    logger.debug("execute, task: {}", task);
    taskRepository.save(task.setState(task.DONE));
    return task;
  }

}
