package com.jupiter.tools.spring.test.core.expected.list.messages;

/**
 * Created on 27.03.2019.
 *
 * This interface used by {@link AssertReceivedMessages} to retrieve message from brokers.
 *
 * You need to wrap your JmsTemplate, AmqpTemplate,
 * or something else to use with the {@link AssertReceivedMessages}.
 *
 * @author Korovin Anatoliy
 */
public interface MessageBroker {

    Object receive(String queueName, long timeout);
}
