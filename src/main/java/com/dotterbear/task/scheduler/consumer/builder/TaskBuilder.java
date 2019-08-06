package com.dotterbear.task.scheduler.consumer.builder;

import com.datastax.driver.core.utils.UUIDs;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;
import com.dotterbear.task.scheduler.consumer.dto.TaskDTO;

public class TaskBuilder {

  public static Task build(TaskDTO taskDto) {
    if (taskDto == null)
      return null;
    Task task = new Task();
    task.setId(UUIDs.timeBased());
    task.setData(taskDto.getData());
    task.setExecTs(taskDto.getExecTs());
    return task;
  }
}
