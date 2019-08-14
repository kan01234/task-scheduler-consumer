package com.dotterbear.task.scheduler.consumer.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

  @Value("${spring.data.cassandra.keyspace-name}")
  private String keyspace;

  @Value("${spring.data.cassandra.contact-points}")
  private String contactPoints;

  @Override
  public String getContactPoints() {
    return contactPoints;
  }

  @Override
  protected List<String> getStartupScripts() {
    final String script =
        "CREATE KEYSPACE IF NOT EXISTS " + keyspace + " WITH durable_writes = true"
            + " AND replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};";
    return Arrays.asList(script);
  }

  @Override
  protected List<String> getShutdownScripts() {
    return Arrays.asList("DROP KEYSPACE IF EXISTS " + keyspace + ";");
  }

  @Override
  public SchemaAction getSchemaAction() {
    return SchemaAction.CREATE_IF_NOT_EXISTS;
  }

  @Override
  protected boolean getMetricsEnabled() {
    return false;
  }

  @Override
  protected String getKeyspaceName() {
    return keyspace;
  }

  @Override
  public String[] getEntityBasePackages() {
    return new String[] {"com.dotterbear.task.scheduler.consumer.cassandra.entity"};
  }
}
