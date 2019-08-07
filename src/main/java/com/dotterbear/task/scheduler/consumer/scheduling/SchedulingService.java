package com.dotterbear.task.scheduler.consumer.scheduling;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingService {

  private static Logger logger = LoggerFactory.getLogger(SchedulingService.class);

  @Autowired
  private ScheduledTaskProducer taskProducer;

  @Autowired
  private ScheduledTaskConsumer taskConsumer;

  @PostConstruct
  public void init() {
    new Thread(taskConsumer).start();
  }

  @Scheduled(cron = "*/30 * * * * *")
  public void executTasks() {
    logger.info("start executTasks");
    taskProducer.run();
    logger.info("end executTasks");
  }

}
