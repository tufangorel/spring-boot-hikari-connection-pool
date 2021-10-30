package com.company.customerinfo.config.shutdown;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
public class TomcatShutdownContextClosedEventConfig implements TomcatConnectorCustomizer,
                                       ApplicationListener<ContextClosedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatShutdownContextClosedEventConfig.class.getName());

    private final ShutdownProperties shutdownProperties;
    private volatile Connector connector;

    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private Environment env;

    public TomcatShutdownContextClosedEventConfig(ShutdownProperties shutdownProperties) {
        this.shutdownProperties = shutdownProperties;
    }

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOGGER.info("Embedded Tomcat stopped accepting new requests");

        if( executor != null )
            executor.setWaitForTasksToCompleteOnShutdown(true);
        else
            LOGGER.info("ThreadPoolTaskExecutor is null!");

        LOGGER.info("Starting shutdown process");
        if( connector != null ) {

            connector.setProperty("maxThreads", "0");
            connector.setProperty("acceptCount", "0");

            Executor executor = connector.getProtocolHandler().getExecutor();
            if (executor instanceof ThreadPoolExecutor) {
                try {
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                    threadPoolExecutor.shutdown();
                    while (!threadPoolExecutor.awaitTermination(shutdownProperties.getTimeout(), MILLISECONDS)) {
                        LOGGER.info("Waiting termination of threads...");
                    }
                } catch (InterruptedException ex) {
                    LOGGER.info("Tomcat termination error!", ex);
                    Thread.currentThread().interrupt();
                }
            }
        }

        LOGGER.info("Shutdown process completed successfully");
    }
}
