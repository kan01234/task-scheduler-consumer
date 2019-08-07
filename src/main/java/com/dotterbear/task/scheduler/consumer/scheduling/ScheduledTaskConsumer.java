package com.dotterbear.task.scheduler.consumer.scheduling;

import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;
import com.dotterbear.task.scheduler.consumer.service.TaskExecuteService;

@Component
public class ScheduledTaskConsumer implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskConsumer.class);

  private final BlockingQueue<Task> queue;

  @Autowired
  private TaskExecuteService taskExecuteService;

  @Autowired
  public ScheduledTaskConsumer(BlockingQueue<Task> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      while (true)
        process(queue.take());
    } catch (Exception e) {
      logger.error("fail to cosumer task", e);
    }
  }

  private void process(Task task) {
    taskExecuteService.execute(task);
  }

}
