package com.dotterbear.task.scheduler.consumer.builder;

import com.dotterbear.task.scheduler.consumer.cassandra.entity.AppNode;

public class AppNodeBuilder {

  public static AppNode build(String ip) {
    if (ip == null || ip.isEmpty())
      return null;
    return new AppNode().setIp(ip);
  }

}
