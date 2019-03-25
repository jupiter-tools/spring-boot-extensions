package com.jupiter.tools.spring.test.rabbitmq.extension;

import com.jupiter.tools.spring.test.rabbitmq.annotation.ExpectedMessages;
import com.jupiter.tools.spring.test.rabbitmq.annotation.meta.EnableRabbitMqTest;
import com.jupiter.tools.spring.test.rabbitmq.extension.pojo.Bar;
import com.jupiter.tools.spring.test.rabbitmq.extension.pojo.Foo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created on 25.03.2019.
 *
 * TODO: replace on javadoc
 *
 * @author Korovin Anatoliy
 */
@EnableRabbitMqTest
@ExtendWith(ExpectedMessagesExtension.class)
class ExpectedMessagesExtensionTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages.json")
    void testSend() {
        amqpTemplate.convertAndSend("test-queue", new Foo("123"));
        amqpTemplate.convertAndSend("test-queue", new Foo("456"));
        amqpTemplate.convertAndSend("test-queue", new Foo("789"));
    }

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages_multiple_types.json")
    void sendMultipleTypes() {
        // first type:
        amqpTemplate.convertAndSend("test-queue", new Foo("123"));
        // second type:
        amqpTemplate.convertAndSend("test-queue", new Bar("AAA", 1));
        amqpTemplate.convertAndSend("test-queue", new Bar("BBB", 2));
        amqpTemplate.convertAndSend("test-queue", new Bar("CCC", 3));
    }

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages.json", ignoreUnexpected = true)
    void testIgnoreUnexpected() {
        amqpTemplate.convertAndSend("test-queue", new Foo("UNEXPECTED"));
        amqpTemplate.convertAndSend("test-queue", new Foo("123"));
        amqpTemplate.convertAndSend("test-queue", new Foo("456"));
        amqpTemplate.convertAndSend("test-queue", new Foo("789"));
    }

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public Queue testQueue() {
            return new Queue("test-queue");
        }
    }

}