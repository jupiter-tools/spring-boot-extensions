package com.jupiter.tools.spring.test.rabbitmq.extension;

import com.jupiter.tools.spring.test.rabbitmq.annotation.ExpectedMessage;
import com.jupiter.tools.spring.test.rabbitmq.annotation.meta.EnableRabbitMqTest;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created on 22.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableRabbitMqTest
public class ExpectedMessageTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    @ExpectedMessage(queue = "test-queue", message = "123")
    void testSend() throws InterruptedException {
        amqpTemplate.convertAndSend("test-queue", "123");
    }

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public Queue testQueue() {
            return new Queue("test-queue");
        }
    }
}
