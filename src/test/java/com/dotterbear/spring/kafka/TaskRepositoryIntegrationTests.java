package com.dotterbear.spring.kafka;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.test.context.junit4.SpringRunner;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.dotterbear.spring.kafka.cassandra.entity.Task;
import com.dotterbear.spring.kafka.cassandra.repository.TaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRepositoryIntegrationTests {

  @Autowired
  private CassandraAdminOperations adminTemplate;

  @Autowired
  private TaskRepository taskRepository;

  @BeforeClass
  public static void startCassandraEmbedded()
      throws ConfigurationException, TTransportException, IOException, InterruptedException {
    EmbeddedCassandraServerHelper.startEmbeddedCassandra();
    Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
    Session session = cluster.connect();
  }

  @AfterClass
  public static void stopCassandraEmbedded() {
    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
  }

  @Before
  public void createTable() {
    adminTemplate.dropTable(CqlIdentifier.cqlId("task"));
    adminTemplate.createTable(true, CqlIdentifier.cqlId("task"), Task.class,
        new HashMap<String, Object>());
  }

  @Test
  public void saveTask() {
    Task task = new Task();
    task.setId(UUIDs.timeBased());
    task.setExecTs(new Date());
    task.setData("data");
    taskRepository.save(task);
    Optional<Task> task2 = taskRepository.findById(task.getId());
    assertEquals(task, task2.orElse(null));
  }
}
