package com.dotterbear.task.scheduler.consumer.cassandra.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Task implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

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

  public Task setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getState() {
    return state;
  }

  public Task setState(String state) {
    this.state = state;
    return this;
  }

  public Date getExecTs() {
    return execTs;
  }

  public Task setExecTs(Date execTs) {
    this.execTs = execTs;
    return this;
  }

  public String getData() {
    return data;
  }

  public Task setData(String data) {
    this.data = data;
    return this;
  }

  @Override
  public String toString() {
    return "Task [id=" + id + ", state=" + state + ", execTs=" + execTs + ", data=" + data + "]";
  }

}
