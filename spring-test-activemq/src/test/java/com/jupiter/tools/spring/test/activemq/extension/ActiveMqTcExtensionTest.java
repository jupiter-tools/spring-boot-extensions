package com.jupiter.tools.spring.test.activemq.extension;

import com.jupiter.tools.spring.test.activemq.annotation.ExpectedMessage;
import com.jupiter.tools.spring.test.activemq.annotation.meta.EnableActiveMqTest;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created on 07.08.2018.
 *
 * @author Korovin Anatoliy
 */
@EnableActiveMqTest
public class ActiveMqTcExtensionTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    @ExpectedMessage(queue = "test-queue", message = "123")
    void testSend() {
        jmsTemplate.convertAndSend("test-queue", "123");
    }

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public Queue testQueue() {
            return new Queue("test-queue");
        }
    }
}
