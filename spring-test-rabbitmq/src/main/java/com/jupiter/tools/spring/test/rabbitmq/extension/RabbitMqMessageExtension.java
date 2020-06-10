package com.jupiter.tools.spring.test.rabbitmq.extension;

import com.jupiter.tools.spring.test.rabbitmq.annotation.ExpectedMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 22.01.2019.
 *
 * @author Korovin Anatoliy
 */
public class RabbitMqMessageExtension implements BeforeAllCallback, AfterEachCallback {

    private AmqpTemplate amqpTemplate;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        ExpectedMessage expectedMessage =
                context.getRequiredTestMethod().getAnnotation(ExpectedMessage.class);

        if (expectedMessage == null) {
            return;
        }

        Message message = amqpTemplate.receive(expectedMessage.queue(), expectedMessage.timeout());

        if(message == null){
            throw new Error(String.format("Expected but not received: %s", expectedMessage.message()));
        }

        Assertions.assertEquals(new String(message.getBody()), expectedMessage.message());
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        amqpTemplate = SpringExtension.getApplicationContext(context)
                                      .getBean(AmqpTemplate.class);

        if (amqpTemplate == null) {
            throw new RuntimeException("Not found the AmqpTemplate bean in the current spring context.");
        }
    }
}
