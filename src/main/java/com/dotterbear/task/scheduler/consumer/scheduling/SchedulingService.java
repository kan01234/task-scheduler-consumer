package com.dotterbear.task.scheduler.consumer.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.dotterbear.task.scheduler.consumer.service.TaskExecuteService;

@Component
public class SchedulingService {

  private static Logger logger = LoggerFactory.getLogger(SchedulingService.class);

  @Autowired
  private TaskExecuteService taskExecuteService;
  
  @Scheduled(cron = "*/30 * * * * *")
  public void executTasks() {
    logger.info("start executTasks");
    taskExecuteService.markProcessing();
    // TODO handle marked processing record
  }

}
