package com.dotterbear.task.scheduler.consumer.cassandra.entity;

import java.util.Date;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("task")
public class Task {

  @PrimaryKey
  private TaskPrimaryKey pk;

  @Column
  private String data;

  public Task() {
  }

  public Task(UUID id, Date execTs, String data) {
    super();
    pk = new TaskPrimaryKey(id, execTs);
    this.data = data;
  }

  public Task(UUID id, Date execTs, String state, String data) {
    super();
    pk = new TaskPrimaryKey(id, execTs, state);
    this.data = data;
  }

  public TaskPrimaryKey getPk() {
    return pk;
  }

  public void setPk(TaskPrimaryKey pk) {
    this.pk = pk;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Task [pk=" + pk + ", data=" + data + "]";
  }

}
