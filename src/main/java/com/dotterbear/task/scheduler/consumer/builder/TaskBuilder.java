package com.dotterbear.task.scheduler.consumer.builder;

import java.util.UUID;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;
import com.dotterbear.task.scheduler.consumer.dto.TaskDTO;

public class TaskBuilder {

  public static Task build(TaskDTO taskDto) {
    if (taskDto == null)
      return null;
    return new Task(UUID.randomUUID(), taskDto.getExecTs(), taskDto.getData());
  }
}
