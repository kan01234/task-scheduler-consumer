package com.dotterbear.task.scheduler.consumer.config;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

  public static Logger logger = LoggerFactory.getLogger(CassandraConfig.class);

  @Value("${spring.data.cassandra.keyspace-name}")
  private String keyspace;

  @Value("${spring.data.cassandra.contact-points}")
  private String contactPoints;

  @Value("${com.dotterbear.cassandra.connection.retry.limit}")
  private int retryLimit;

  @Value("${com.dotterbear.cassandra.connection.retry.after}")
  private long retryAfter;


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

  @Override
  public CassandraSessionFactoryBean session() {
    return session(0);
  }

  public CassandraSessionFactoryBean session(int count) {
    try {
      return super.session();
    } catch (Exception e) {
      if (count++ < retryLimit) {
        sleep(retryAfter);
        return session(count);
      } else
        throw e;
    }
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      logger.error("fail to sleep", e);
    }
  }

}
