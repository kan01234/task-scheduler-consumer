package com.dotterbear.spring.kafka;

import java.io.Serializable;
import java.util.Date;

public class TaskDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Date execTs;

  private String data;

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public Date getExecTs() {
    return execTs;
  }

  public void setExecTs(Date execTs) {
    this.execTs = execTs;
  }

  @Override
  public String toString() {
    return "Task [execTs=" + execTs + ", data=" + data + "]";
  }

}
