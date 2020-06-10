package com.jupiter.tools.spring.test.rabbitmq.extension;

import java.util.Date;

import com.jupiter.tools.spring.test.rabbitmq.annotation.EnableRabbitMqTestContainers;
import com.jupiter.tools.spring.test.rabbitmq.annotation.ExpectedMessages;
import com.jupiter.tools.spring.test.rabbitmq.extension.expected.list.messages.RabbitMqExpectedListOfMessagesExtension;
import com.jupiter.tools.spring.test.rabbitmq.extension.pojo.Bar;
import com.jupiter.tools.spring.test.rabbitmq.extension.pojo.Foo;
import com.jupiter.tools.spring.test.rabbitmq.extension.pojo.FooWithBar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 25.03.2019.
 *
 * @author Korovin Anatoliy
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableRabbitMqTestContainers
@ExtendWith(RabbitMqExpectedListOfMessagesExtension.class)
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
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages_with_nested_objects.json")
    void testSendObjectWithNested() {

        Foo childFoo = new Foo("child foo");
        Bar childBar = new Bar("child bar", 1);
        FooWithBar child = FooWithBar.builder()
                                     .foo(childFoo)
                                     .bar(childBar)
                                     .build();

        Foo foo = new Foo("parent foo");
        Bar bar = new Bar("parent bar", 2);
        FooWithBar fooWithBar = FooWithBar.builder()
                                     .foo(foo)
                                     .bar(bar)
                                     .child(child)
                                     .build();

        amqpTemplate.convertAndSend("test-queue", fooWithBar);
    }

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_partial.json")
    void testExpectedPartialFields() {

        Foo childFoo = new Foo("child foo");
        Bar childBar = new Bar("child bar", 1);
        FooWithBar child = FooWithBar.builder()
                                     .foo(childFoo)
                                     .bar(childBar)
                                     .build();

        Foo foo = new Foo("parent foo");
        Bar bar = new Bar("parent bar", 2);
        FooWithBar fooWithBar = FooWithBar.builder()
                                          .foo(foo)
                                          .bar(bar)
                                          .child(child)
                                          .build();

        amqpTemplate.convertAndSend("test-queue", fooWithBar);
    }

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages_with_js.json")
    void testExpectedDataSetWithJavaScript() {
        amqpTemplate.convertAndSend("test-queue", new Foo( String.valueOf(1+2+3+4+5)));
    }

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages_with_date.json")
    void testExpectedWithDate() {
        // Arrange
        FooWithBar fooWithBar = FooWithBar.builder()
                                          // NOW
                                          .time(new Date())
                                          .build();
        // Act
        amqpTemplate.convertAndSend("test-queue", fooWithBar);
    }

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public Queue testQueue() {
            return new Queue("test-queue");
        }

        @Bean
        public MessageConverter jackson2JsonMessageConverter() {
            return new Jackson2JsonMessageConverter();
        }
    }

}