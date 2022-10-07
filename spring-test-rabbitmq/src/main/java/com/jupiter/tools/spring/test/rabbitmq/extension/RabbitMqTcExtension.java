package com.jupiter.tools.spring.test.rabbitmq.extension;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.GenericContainer;

/**
 * Created on 07.08.2018.
 *
 * @author Korovin Anatoliy
 */
public class RabbitMqTcExtension implements Extension {

    private static final Integer RABBIT_PORT = 5672;
    private static final GenericContainer rabbitmq =
            new GenericContainer("rabbitmq:3.9.23-management-alpine").withExposedPorts(RABBIT_PORT, 15672);

    static {
        rabbitmq.start();
        rabbitmq.withReuse(true);
        System.setProperty("spring.rabbitmq.host", rabbitmq.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitmq.getMappedPort(RABBIT_PORT).toString());
    }
}
