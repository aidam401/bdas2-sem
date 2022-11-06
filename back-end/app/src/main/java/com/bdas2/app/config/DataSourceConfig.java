package com.bdas2.app.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    final Environment env;

    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getConfig(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(env.getProperty("database.connection.jdbc.driver"));
        hikariConfig.setJdbcUrl(env.getProperty("database.connection.jdbc.url"));
        hikariConfig.setUsername(env.getProperty("database.connection.jdbc.username"));
        hikariConfig.setPassword(env.getProperty("database.connection.jdbc.password"));

        return new HikariDataSource(hikariConfig);
    }


}
