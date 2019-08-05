package com.dotterbear.task.scheduler.consumer;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.datastax.driver.core.utils.UUIDs;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;
import com.dotterbear.task.scheduler.consumer.cassandra.repository.TaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRepositoryIntegrationTests {

  @Autowired
  private TaskRepository taskRepository;

  @Test
  public void saveTask() {
    Task task = new Task();
    task.setId(UUIDs.timeBased());
    task.setExecTs(new Date());
    task.setData("data");
    taskRepository.save(task);
    Optional<Task> task2 = taskRepository.findById(task.getId());
    assertEquals(task.toString(), task2.orElse(new Task()).toString());
  }
}
