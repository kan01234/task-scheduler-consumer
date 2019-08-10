package com.dotterbear.task.scheduler.consumer.cassandra.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.assertj.core.util.Arrays;
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
    // TODO add db lock?
    appNode.setIsMaster(appNodeRepository
        .findByIsMasterAndPingTsGreaterThanEqual(Boolean.TRUE, calcAssumeAliveTs()).isEmpty());
    appNodeRepository.save(appNode);
  }

  public Iterable<AppNode> findAll() {
    return appNodeRepository.findAll();
  }

  public List<AppNode> getAliveAppNodes() {
    return appNodeRepository.findByPingTsGreaterThanEqual(calcAssumeAliveTs());
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

  public Boolean isMaster() {
    return appNodeRepository.findById(appNode.getId()).get().getIsMaster();
  }

  public void checkMaster() {
    List<AppNode> appNodes = new ArrayList<AppNode>();
    appNodeRepository.findAll().iterator().forEachRemaining(appNodes::add);
    List<AppNode> masterNodes = appNodes.stream()
        .filter(appNode -> appNode.getIsMaster() == Boolean.TRUE)
        .collect(Collectors.toList());
    AppNode masterNode = masterNodes.isEmpty() ? null : masterNodes.get(0);
    if (!isAlive(masterNode)) {
      AppNode newMasterNode = appNodes.stream()
          .filter(appNode -> appNode.getIsMaster() == Boolean.FALSE)
          .min(Comparator.comparing(AppNode::getCreatedTs))
          .orElseThrow(NoSuchElementException::new)
          .setIsMaster(Boolean.TRUE);
      appNodeRepository.save(newMasterNode);
      if (masterNode != null)
        appNodeRepository.save(masterNode.setIsMaster(Boolean.FALSE));
      logger.debug("master node changed, old: {}, new: {}", masterNode, newMasterNode);
    }
  }

  private Long calcAssumeAliveTs() {
    return System.currentTimeMillis() - assumeAlive * 1000;
  }

  private Boolean isAlive(AppNode appNode) {
    if (appNode == null || appNode.getPingTs() == null)
      return Boolean.FALSE;
    return appNode.getPingTs().getTime() >= calcAssumeAliveTs();
  }
}
