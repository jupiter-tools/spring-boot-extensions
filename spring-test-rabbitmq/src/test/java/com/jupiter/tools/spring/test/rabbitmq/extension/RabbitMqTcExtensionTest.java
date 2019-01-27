package com.jupiter.tools.spring.test.rabbitmq.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Created on 23.01.2019.
 *
 * @author Korovin Anatoliy
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(RabbitMqTcExtension.class)
class RabbitMqTcExtensionTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    void testSend() {
        // Arrange
        // Act
        amqpTemplate.convertAndSend("test-queue", "123");

        await().atMost(3, TimeUnit.SECONDS)
               .until(() -> TestConfig.events.size() > 0);

        // Asserts
        assertThat(TestConfig.events).containsOnly("123");
    }

    @TestConfiguration
    public static class TestConfig {

        public static List<String> events = new ArrayList<>();

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean
        public Queue testQueue() {
            return new Queue("test-queue");
        }

        @Component
        @EnableRabbit
        public class TestRabbitListener {

            @RabbitListener(queues = "test-queue")
            public void receive(String message) {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                events.add(message);
                System.out.println("!!!! " + message);
            }
        }
    }
}