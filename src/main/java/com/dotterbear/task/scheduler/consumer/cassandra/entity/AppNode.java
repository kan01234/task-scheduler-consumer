package com.dotterbear.task.scheduler.consumer.cassandra.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class AppNode implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 2L;

  @Id
  private UUID id;

  private String ip;

  private Date createdTs = new Date();

  private Date pingTs = new Date();

  private Boolean isMaster = Boolean.FALSE;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public AppNode setIp(String ip) {
    this.ip = ip;
    return this;
  }

  public Date getCreatedTs() {
    return createdTs;
  }

  public AppNode setCreatedTs(Date createdTs) {
    this.createdTs = createdTs;
    return this;
  }

  public Date getPingTs() {
    return pingTs;
  }

  public AppNode setPingTs(Date pingTs) {
    this.pingTs = pingTs;
    return this;
  }

  public Boolean getIsMaster() {
    return isMaster;
  }

  public AppNode setIsMaster(Boolean isMaster) {
    this.isMaster = isMaster;
    return this;
  }

  @Override
  public String toString() {
    return "AppNode [id=" + id + ", ip=" + ip + ", createdTs=" + createdTs + ", pingTs=" + pingTs
        + ", isMaster=" + isMaster + "]";
  }

}
