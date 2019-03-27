package com.jupiter.tools.spring.test.activemq.extension.expectedmess;

/**
 * Created on 27.03.2019.
 *
 * TODO: replace on javadoc
 *
 * @author Korovin Anatoliy
 */
public interface MessageBroker {

    Object receive(String queueName, long timeout);
}
