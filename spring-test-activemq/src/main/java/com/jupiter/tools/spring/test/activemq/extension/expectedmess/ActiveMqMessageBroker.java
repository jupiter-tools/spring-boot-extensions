package com.jupiter.tools.spring.test.activemq.extension.expectedmess;

import org.springframework.jms.core.JmsTemplate;

/**
 * Created on 27.03.2019.
 *
 * TODO: replace on javadoc
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
