package com.jupiter.tools.spring.test.activemq.extension;

import java.util.Date;

import com.jupiter.tools.spring.test.activemq.annotation.ExpectedMessages;
import com.jupiter.tools.spring.test.activemq.annotation.meta.EnableActiveMqTest;
import com.jupiter.tools.spring.test.activemq.extension.expected.Bar;
import com.jupiter.tools.spring.test.activemq.extension.expected.Foo;
import com.jupiter.tools.spring.test.activemq.extension.expected.FooWithBar;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Created on 25.03.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableActiveMqTest
public class ActiveMqSmartDataSetTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages.json")
    void testSendListOfMessages() {
        jmsTemplate.convertAndSend("test-queue", new Foo("123"));
        jmsTemplate.convertAndSend("test-queue", new Foo("456"));
        jmsTemplate.convertAndSend("test-queue", new Foo("789"));
    }

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages_with_js.json")
    void testExpectedDataSetWithJavaScript() {
        jmsTemplate.convertAndSend("test-queue", new Foo( String.valueOf(1+2+3+4+5)));
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
        jmsTemplate.convertAndSend("test-queue", fooWithBar);
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

        jmsTemplate.convertAndSend("test-queue", fooWithBar);
    }

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public Queue testQueue() {
            return new Queue("test-queue");
        }

        @Bean // Serialize message content to json using TextMessage
        public MessageConverter jacksonJmsMessageConverter() {
            MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
            converter.setTargetType(MessageType.TEXT);
            converter.setTypeIdPropertyName("_type");
            return converter;
        }
    }
}
