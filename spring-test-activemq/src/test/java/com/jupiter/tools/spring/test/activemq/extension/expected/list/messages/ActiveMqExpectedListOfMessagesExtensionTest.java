package com.jupiter.tools.spring.test.activemq.extension.expected.list.messages;

import com.jupiter.tools.spring.test.activemq.annotation.ExpectedMessages;
import com.jupiter.tools.spring.test.activemq.annotation.meta.EnableActiveMqTest;
import com.jupiter.tools.spring.test.activemq.extension.expected.Foo;
import com.jupiter.tools.spring.test.activemq.extension.expected.list.messages.ActiveMqExpectedListOfMessagesExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Created on 26.03.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableActiveMqTest
@ExtendWith(ActiveMqExpectedListOfMessagesExtension.class)
class ActiveMqExpectedListOfMessagesExtensionTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_empty_list.json")
    void testEmptyListInFile() {
        //NOP
    }

    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_empty_file.json")
    void testEmptyFile() {
        //NOP
    }

    @Disabled("TODO: find a way to test exceptions in extensions")
    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_empty_list.json")
    void testEmptyListInFileWithUnexpectedSending() {
        jmsTemplate.convertAndSend("test-queue", new Foo("789"));
    }

    @Disabled("TODO: find a way to test exceptions in extensions")
    @Test
    @ExpectedMessages(queue = "test-queue", messagesFile = "/datasets/expected_empty_file.json")
    void testEmptyFileWithUnexpectedSending() {
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