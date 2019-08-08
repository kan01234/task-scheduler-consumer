package com.dotterbear.task.scheduler.consumer;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.dotterbear.task.scheduler.consumer.builder.AppNodeBuilder;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.AppNode;
import com.dotterbear.task.scheduler.consumer.cassandra.service.AppNodeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppNodeTests {

  @Autowired
  private AppNodeService appNodeService;

  @Test
  public void testGetAllAliveAppNode() {
    appNodeService.deleteAll();
    AppNode node1 = AppNodeBuilder.build("127.0.0.1");
    appNodeService.save(node1);
    AppNode node2 = AppNodeBuilder.build("127.0.0.2").setPingTs(new Date(System.currentTimeMillis() - 70 * 1000));
    appNodeService.save(node2);
    List<AppNode> aliveNodes = appNodeService.getAliveAppNodes();
    assertEquals(aliveNodes.size(), 1);
    assertEquals(aliveNodes.get(0).getId(), node1.getId());
  }

}
