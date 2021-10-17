package com.company.customerinfo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;


@Component
public class MonitorHikariDataSourceConfig implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger log = LoggerFactory.getLogger(MonitorHikariDataSourceConfig.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        String hikariConnectionPoolName = env.getProperty("spring.datasource.poolName");
        HikariCPStatisticsBackgroundThread hikariCPStatisticsBackgroundThread = new HikariCPStatisticsBackgroundThread(hikariConnectionPoolName);
        taskExecutor.execute(hikariCPStatisticsBackgroundThread );
    }

}