package com.jupiter.tools.spring.test.activemq.extension.expected.list.messages;

import com.jupiter.tools.spring.test.activemq.annotation.ExpectedMessages;
import com.jupiter.tools.spring.test.activemq.annotation.meta.EnableActiveMqTest;
import com.jupiter.tools.spring.test.activemq.extension.expected.Bar;
import com.jupiter.tools.spring.test.activemq.extension.expected.Foo;
import com.jupiter.tools.spring.test.activemq.extension.expected.list.messages.ActiveMqExpectedListOfMessagesExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Created on 07.08.2018.
 *
 * @author Korovin Anatoliy
 */
@SpringBootTest
@EnableActiveMqTest
@ExtendWith(ActiveMqExpectedListOfMessagesExtension.class)
public class ExpectedListOfMessagesTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages.json")
    void testSend() {
        jmsTemplate.convertAndSend("test-queue", new Foo("123"));
        jmsTemplate.convertAndSend("test-queue", new Foo("456"));
        jmsTemplate.convertAndSend("test-queue", new Foo("789"));
    }

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages_multiple_types.json")
    void sendMultipleTypes() {
        // first type:
        jmsTemplate.convertAndSend("test-queue", new Foo("123"));
        // second type:
        jmsTemplate.convertAndSend("test-queue", new Bar("AAA", 1));
        jmsTemplate.convertAndSend("test-queue", new Bar("BBB",2));
        jmsTemplate.convertAndSend("test-queue", new Bar("CCC",3));
    }

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_messages.json", ignoreUnexpected = true)
    void testIgnoreUnexpected() {
        jmsTemplate.convertAndSend("test-queue", new Foo("UNEXPECTED"));
        jmsTemplate.convertAndSend("test-queue", new Foo("123"));
        jmsTemplate.convertAndSend("test-queue", new Foo("456"));
        jmsTemplate.convertAndSend("test-queue", new Foo("789"));
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
