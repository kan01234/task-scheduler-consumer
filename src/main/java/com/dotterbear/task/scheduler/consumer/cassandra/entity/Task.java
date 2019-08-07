package com.dotterbear.task.scheduler.consumer.cassandra.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Task implements Serializable {

  public static final String DONE = "D";

  public static final String INIT = "I";

  public static final String PROCESSING = "P";

  @Id
  private UUID id;

  private String state = INIT;

  private Date execTs;

  private String data;

  public Task() {}

  public Task(UUID id, Date execTs, String data) {
    super();
    this.id = id;
    this.execTs = execTs;
    this.data = data;
  }

  public Task(UUID id, Date execTs, String data, String state) {
    super();
    this.id = id;
    this.execTs = execTs;
    this.data = data;
    this.state = state;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
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

  @Override
  public String toString() {
    return "Task [id=" + id + ", state=" + state + ", execTs=" + execTs + ", data=" + data + "]";
  }

}
