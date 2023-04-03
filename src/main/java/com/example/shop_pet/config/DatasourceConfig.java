package com.example.shop_pet.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatasourceConfig {
  Logger logger = LoggerFactory.getLogger(getClass());

  public DatasourceConfig() {
    logger.info("Loaded DatasourceConfig");
  }

  @Bean
  @ConfigurationProperties("app.datasource.main")
  public HikariDataSource hikariDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean
  public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
    return new JdbcTemplate(hikariDataSource);
  }
}
