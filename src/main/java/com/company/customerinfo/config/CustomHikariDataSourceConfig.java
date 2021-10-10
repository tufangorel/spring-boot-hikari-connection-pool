package com.company.customerinfo.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "customEntityManagerFactory",
        basePackages = {"com.company.customerinfo.repository"},
        transactionManagerRef = "customTransactionManager"
)
public class CustomHikariDataSourceConfig {

    @Primary
    @Bean(name = "customHikariDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource customHikariDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "customEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("customHikariDataSource") DataSource customHikariDataSource
    ) {
        return builder
                .dataSource(customHikariDataSource)
                .packages("com.company.customerinfo.model")
                .persistenceUnit("customHikariDataSourcePersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "customTransactionManager")
    public PlatformTransactionManager customTransactionManager (
            @Qualifier("customEntityManagerFactory")EntityManagerFactory customEntityManagerFactory){
        return new JpaTransactionManager(customEntityManagerFactory);
    }

}