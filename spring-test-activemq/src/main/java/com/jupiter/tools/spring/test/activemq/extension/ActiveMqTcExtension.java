package com.jupiter.tools.spring.test.activemq.extension;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.GenericContainer;

/**
 * Created on 07.08.2018.
 *
 * @author Korovin Anatoliy
 */
public class ActiveMqTcExtension implements Extension {

    private static final Integer MQ_PORT = 61616;
    private static GenericContainer activemq =
            new GenericContainer("rmohr/activemq:latest").withExposedPorts(MQ_PORT);

    static {

        activemq.start();

        String brokerUrl = String.format("tcp://%s:%s",
                                         activemq.getContainerIpAddress(),
                                         activemq.getMappedPort(MQ_PORT));

        System.setProperty("spring.activemq.broker-url", brokerUrl);
        System.setProperty("spring.activemq.user", "admin");
        System.setProperty("spring.activemq.password", "secret");
    }
}
