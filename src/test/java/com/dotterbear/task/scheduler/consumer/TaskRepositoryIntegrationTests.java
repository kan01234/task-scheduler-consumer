package com.dotterbear.task.scheduler.consumer;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.dotterbear.task.scheduler.consumer.builder.TaskBuilder;
import com.dotterbear.task.scheduler.consumer.cassandra.entity.Task;
import com.dotterbear.task.scheduler.consumer.cassandra.repository.TaskRepository;
import com.dotterbear.task.scheduler.consumer.dto.TaskDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRepositoryIntegrationTests {

  @Autowired
  private TaskRepository taskRepository;

//  @Test
  public void saveTask() {
    Task task = random();
    taskRepository.save(task);
    Optional<Task> task2 = taskRepository.findById(task.getId());
    assertEquals(task.toString(), task2.orElse(new Task()).toString());
  }

  @Test
  public void saveTasks() {
    taskRepository.deleteAll();
    List<Task> tasks = new ArrayList<Task>();
    IntStream.range(0, 2).forEach(i -> tasks.add(random()));
    taskRepository.saveAll(tasks);
    Collections.reverse(tasks);
    assertEquals(tasks.toString(), taskRepository.findAll().toString());
  }

  private Task random() {
    TaskDTO taskDto = new TaskDTO();
    taskDto.setData("data");
    taskDto.setExecTs(new Date());
    return TaskBuilder.build(taskDto);
  }
}
