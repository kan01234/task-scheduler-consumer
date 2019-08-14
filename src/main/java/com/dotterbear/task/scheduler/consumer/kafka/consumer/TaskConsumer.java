package com.dotterbear.task.scheduler.consumer.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.dotterbear.task.scheduler.consumer.builder.TaskBuilder;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;
import com.dotterbear.task.scheduler.consumer.cassandra.repository.TaskRepository;
import com.dotterbear.task.scheduler.consumer.dto.TaskDTO;

//@Component
public class TaskConsumer {

  private static final Logger logger = LoggerFactory.getLogger(TaskConsumer.class);

  @Autowired
  private TaskRepository taskRepository;

  @KafkaListener(topics = {"scheduler-task"}, groupId = "scheduler-task-consumer-grp1", containerFactory = "kafkaListenerContainerFactory")
  public void listen(TaskDTO taskDto) {
    logger.debug("grp1 receive task: {}", taskDto);
    Task task = TaskBuilder.build(taskDto);
    taskRepository.save(task);
  }

}
