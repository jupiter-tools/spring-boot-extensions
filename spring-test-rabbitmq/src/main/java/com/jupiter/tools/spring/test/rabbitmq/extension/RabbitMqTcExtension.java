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
    private static GenericContainer rabbitmq =
            new GenericContainer("rabbitmq:management").withExposedPorts(RABBIT_PORT, 15672);

    static {
        rabbitmq.start();
        System.setProperty("spring.rabbitmq.host", rabbitmq.getContainerIpAddress());
        System.setProperty("spring.rabbitmq.port", rabbitmq.getMappedPort(RABBIT_PORT).toString());
    }
}
