package com.company.customerinfo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class StartupApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupApplicationListener.class);

    @Autowired
    private DBHealthCheckConfig dbHealthCheckConfig;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if( dbHealthCheckConfig.health().getStatus() == Status.UP )
            LOGGER.info("Initial Database Connection Check Success");
        else
            LOGGER.info("Initial Database Connection Check Fail");
    }
}