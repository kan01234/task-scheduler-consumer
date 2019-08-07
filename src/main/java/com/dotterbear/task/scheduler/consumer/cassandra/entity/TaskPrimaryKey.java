package com.dotterbear.task.scheduler.consumer.cassandra.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class TaskPrimaryKey implements Serializable {

  public static final String DONE = "D";

  public static final String INIT = "I";

  public static final String PROCESSING = "P";

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.CLUSTERED)
  private UUID id;

  @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
  private String state = INIT;

  @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
  private Date execTs;

  public TaskPrimaryKey() {
  }

  public TaskPrimaryKey(UUID id, Date execTs) {
    super();
    this.id = id;
    this.execTs = execTs;
  }

  public TaskPrimaryKey(UUID id, Date execTs, String state) {
    super();
    this.id = id;
    this.execTs = execTs;
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

  @Override
  public int hashCode() {
    final int prime = 4;
    int result = 1;
    result = prime * result + ((execTs == null) ? 0 : execTs.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    return hashCode() == obj.hashCode();
  }

  @Override
  public String toString() {
    return "TaskPrimaryKey [id=" + id + ", state=" + state + ", execTs=" + execTs + "]";
  }

}
