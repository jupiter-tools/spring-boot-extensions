package com.jupiter.tools.spring.test.activemq.extension;

import javax.jms.Message;

import com.jupiter.tools.spring.test.activemq.annotation.ExpectedMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 22.01.2019.
 *
 * @author Korovin Anatoliy
 */
public class ActiveMqMessageExtension implements BeforeAllCallback, AfterEachCallback {

    private JmsTemplate jmsTemplate;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        ExpectedMessage expectedMessage = context.getRequiredTestMethod()
                                                 .getAnnotation(ExpectedMessage.class);

        if(expectedMessage ==null){
            return;
        }

        jmsTemplate.setReceiveTimeout(expectedMessage.timeout());
        Message message = jmsTemplate.receive(expectedMessage.queue());
        assertThat(((ActiveMQTextMessage) message).getText()).isEqualTo(expectedMessage.message());
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        jmsTemplate = SpringExtension.getApplicationContext(context)
                                     .getBean(JmsTemplate.class);

        if (jmsTemplate == null) {
            throw new RuntimeException("Not found the JmsTemplate bean in the current spring context.");
        }
    }
}
