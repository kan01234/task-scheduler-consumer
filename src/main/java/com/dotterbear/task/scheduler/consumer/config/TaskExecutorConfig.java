package com.dotterbear.task.scheduler.consumer.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;

@Configuration
public class TaskExecutorConfig {

  @Bean
  public BlockingQueue<Task> getBlockingQueue() {
    return new ArrayBlockingQueue<Task>(10);
  }

}
