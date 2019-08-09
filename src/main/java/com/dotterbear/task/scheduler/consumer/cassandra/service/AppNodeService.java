package com.dotterbear.task.scheduler.consumer.cassandra.service;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.dotterbear.task.scheduler.consumer.builder.AppNodeBuilder;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.AppNode;
import com.dotterbear.task.scheduler.consumer.cassandra.repository.AppNodeRepository;

@Service
public class AppNodeService {

  private static final Logger logger = LoggerFactory.getLogger(AppNodeService.class);

  private static AppNode appNode;

  @Value("${com.dotterbear.nodes.scheduler.assume.alive}")
  private int assumeAlive;

  @Autowired
  private AppNodeRepository appNodeRepository;

  @PostConstruct
  public void init() {
    // TODO set ip
    appNode = AppNodeBuilder.build("127.0.0.1");
    appNodeRepository.save(appNode);
  }

  public Iterable<AppNode> findAll() {
    return appNodeRepository.findAll();
  }

  public List<AppNode> getAliveAppNodes() {
    return appNodeRepository
        .findByPingTsGreaterThanEqual(new Date().getTime() - assumeAlive * 1000);
  }

  public AppNode save(AppNode appNode) {
    return appNodeRepository.save(appNode);
  }

  public void deleteAll() {
    appNodeRepository.deleteAll();
  }

  public AppNode sendHeartBeat() {
    return appNodeRepository.save(appNode.setPingTs(new Date()));
  }

}
