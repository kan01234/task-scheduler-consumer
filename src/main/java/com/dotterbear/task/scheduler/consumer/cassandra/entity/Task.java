package com.dotterbear.task.scheduler.consumer.cassandra.entity;

import java.util.Date;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("task")
public class Task {

  public static final String DONE = "D";

  public static final String INIT = "I";

  public static final String PROCESSING = "P";

  @Id
  private UUID id;

  private String state = INIT;

  @Indexed("name")
  private Date execTs;

  @Column
  private String data;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getExecTs() {
    return execTs;
  }

  public void setExecTs(Date execTs) {
    this.execTs = execTs;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "Task [id=" + id + ", execTs=" + execTs + ", data=" + data + ", state=" + state + "]";
  }

}
