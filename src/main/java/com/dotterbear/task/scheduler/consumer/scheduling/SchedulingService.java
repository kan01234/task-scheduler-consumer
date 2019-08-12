package com.dotterbear.task.scheduler.consumer.scheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.dotterbear.task.scheduler.consumer.cassandra.service.AppNodeService;

@Component
public class SchedulingService {

  private static Logger logger = LoggerFactory.getLogger(SchedulingService.class);

  @Autowired
  private ScheduledTaskProducer taskProducer;

  @Autowired
  private ScheduledTaskConsumer taskConsumer;

  @Autowired
  private AppNodeService appNodeService;

  @Value("${com.dotterbear.task.consumer.thread}")
  private int thread;

  @PostConstruct
  public void init() {
    ExecutorService executorService = Executors.newFixedThreadPool(thread);
    for(int i = 0; i < thread; i++)
      executorService.execute(taskConsumer);
  }

  @Scheduled(cron = "*/30 * * * * *")
  public void executeTasks() {
    if (!appNodeService.isMaster()) {
      logger.debug("not is the master in the group, skipped");
      return;
    }
    logger.info("start executTasks");
    taskProducer.run();
    logger.info("end executTasks");
  }

  @Scheduled(cron = "0/15 * * * * *")
  public void executeSendHeartBeat() {
    logger.debug("start executeSendHeartBeat");
    appNodeService.sendHeartBeat();
    logger.debug("end executeSendHeartBeat");
  }

  @Scheduled(cron = "0 0/5 * * * *")
  public void executeLeaderResolution() {
    logger.debug("start executeLeaderResolution");
    appNodeService.checkMaster();
    logger.debug("end executeLeaderResolution");
  }


}
