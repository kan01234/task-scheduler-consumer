package com.dotterbear.task.scheduler.consumer.dto;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class TaskDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
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
