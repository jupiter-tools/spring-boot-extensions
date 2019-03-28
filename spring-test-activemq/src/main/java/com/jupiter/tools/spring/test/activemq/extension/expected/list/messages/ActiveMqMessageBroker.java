package com.jupiter.tools.spring.test.activemq.extension.expected.list.messages;

import com.jupiter.tools.spring.test.core.expected.list.messages.AssertReceivedMessages;
import com.jupiter.tools.spring.test.core.expected.list.messages.MessageBroker;

import org.springframework.jms.core.JmsTemplate;

/**
 * Created on 27.03.2019.
 *
 * Wrapped the JmsTemplate, to use in
 * the {@link AssertReceivedMessages}
 *
 * @author Korovin Anatoliy
 */
public class ActiveMqMessageBroker implements MessageBroker {

    private final JmsTemplate jmsTemplate;

    public ActiveMqMessageBroker(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Object receive(String queueName, long timeout) {
        jmsTemplate.setReceiveTimeout(timeout);
        return jmsTemplate.receiveAndConvert(queueName);
    }
}
