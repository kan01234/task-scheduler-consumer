package com.dotterbear.task.scheduler.consumer.cassandra.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
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
    String ip = "";
    try {
      ip = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      logger.error("fail to get ip", e);
    }
    appNode = AppNodeBuilder.build(ip);
    // TODO add db lock?
    appNode.setIsMaster(getAliveMasterNodes().isEmpty());
    appNodeRepository.save(appNode);
  }

  public List<AppNode> getAliveMasterNodes() {
    return appNodeRepository.findByIsMasterAndPingTsGreaterThanEqual(Boolean.TRUE,
        calcAssumeAliveTs());
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
    List<AppNode> appNodes = getAliveAppNodes();
    List<AppNode> masterNodes = appNodes.stream()
        .filter(appNode -> appNode.getIsMaster() == Boolean.TRUE).collect(Collectors.toList());
    AppNode masterNode = masterNodes.isEmpty() ? null : masterNodes.get(0);
    if (!isAlive(masterNode)) {
      AppNode newMasterNode =
          appNodes.stream().filter(appNode -> appNode.getIsMaster() == Boolean.FALSE)
              .min(Comparator.comparing(AppNode::getCreatedTs))
              .orElseThrow(NoSuchElementException::new).setIsMaster(Boolean.TRUE);
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
