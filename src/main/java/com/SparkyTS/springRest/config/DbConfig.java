package com.SparkyTS.springRest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DbConfig {

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/innotest?useSSL=false");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("");
        return driverManagerDataSource;
    }
}
