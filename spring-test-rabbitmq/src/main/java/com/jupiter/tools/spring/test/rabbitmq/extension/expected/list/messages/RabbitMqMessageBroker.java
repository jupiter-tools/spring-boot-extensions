package com.jupiter.tools.spring.test.rabbitmq.extension.expected.list.messages;

import com.jupiter.tools.spring.test.core.expected.list.messages.AssertReceivedMessages;
import com.jupiter.tools.spring.test.core.expected.list.messages.MessageBroker;

import org.springframework.amqp.core.AmqpTemplate;

/**
 * Created on 27.03.2019.
 *
 * Wrapped the AmqpTemplate, to use in
 * the {@link AssertReceivedMessages}
 *
 * @author Korovin Anatoliy
 */
public class RabbitMqMessageBroker implements MessageBroker {

    private final AmqpTemplate amqpTemplate;

    public RabbitMqMessageBroker(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public Object receive(String queueName, long timeout) {
        return amqpTemplate.receiveAndConvert(queueName, timeout);
    }
}
