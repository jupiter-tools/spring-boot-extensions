package com.jupiter.tools.spring.test.activemq.extension;

import com.jupiter.tools.spring.test.activemq.annotation.ExpectedMessages;
import com.jupiter.tools.spring.test.activemq.extension.expectedmess.ActiveMqMessageBroker;
import com.jupiter.tools.spring.test.activemq.extension.expectedmess.AssertReceivedMessages;
import com.jupiter.tools.spring.test.activemq.extension.expectedmess.MessageBroker;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 07.02.2019.
 *
 * This extension provides an ability to assert sent messages after test execution.
 * You can declare a list of expected messages in JSON format.
 *
 * @author Korovin Anatoliy
 */
public class ExpectedMessagesExtension implements BeforeAllCallback, AfterEachCallback {

    private MessageBroker messageBroker;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        ExpectedMessages expectedMessages = context.getRequiredTestMethod()
                                                   .getAnnotation(ExpectedMessages.class);

        new AssertReceivedMessages(expectedMessages, messageBroker).doAssert();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        JmsTemplate jmsTemplate = SpringExtension.getApplicationContext(context)
                                                 .getBean(JmsTemplate.class);

        if (jmsTemplate == null) {
            throw new RuntimeException("Not found the JmsTemplate bean in the current spring context.");
        }

        messageBroker = new ActiveMqMessageBroker(jmsTemplate);
    }
}
