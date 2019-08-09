package com.dotterbear.task.scheduler.consumer;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import com.dotterbear.task.scheduler.consumer.builder.AppNodeBuilder;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.AppNode;
import com.dotterbear.task.scheduler.consumer.cassandra.service.AppNodeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppNodeTests {

  @Autowired
  private AppNodeService appNodeService;

  @Test
  // testGetAllAliveAppNode
  public void test2() {
    appNodeService.deleteAll();
    AppNode node1 = AppNodeBuilder.build("127.0.0.1");
    appNodeService.save(node1);
    AppNode node2 = AppNodeBuilder.build("127.0.0.2")
        .setPingTs(new Date(System.currentTimeMillis() - 70 * 1000));
    appNodeService.save(node2);
    List<AppNode> aliveNodes = appNodeService.getAliveAppNodes();
    assertEquals(aliveNodes.size(), 1);
    assertEquals(aliveNodes.get(0).getId(), node1.getId());
  }

  @Test
  // testAppNodeIsMasterInit
  public void test1() {
    assertEquals(appNodeService.isMaster(), Boolean.TRUE);
    appNodeService.deleteAll();
    appNodeService.save(AppNodeBuilder.build("127.0.0.1").setIsMaster(Boolean.TRUE));
    appNodeService.init();
    assertEquals(appNodeService.isMaster(), Boolean.FALSE);
    appNodeService.deleteAll();
    appNodeService.save(AppNodeBuilder.build("127.0.0.1").setIsMaster(Boolean.TRUE)
        .setPingTs(new Date(System.currentTimeMillis() - 70 * 1000)));
    appNodeService.init();
    assertEquals(appNodeService.isMaster(), Boolean.TRUE);
  }

}
