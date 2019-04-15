package com.jupiter.tools.spring.test.rabbitmq.annotation.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.rabbitmq.annotation.EnableRabbitMqTestContainers;
import com.jupiter.tools.spring.test.rabbitmq.extension.RabbitMqMessageExtension;
import com.jupiter.tools.spring.test.rabbitmq.extension.expected.list.messages.RabbitMqExpectedListOfMessagesExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 07.08.2018.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableRabbitMqTestContainers
@ExtendWith(RabbitMqMessageExtension.class)
@ExtendWith(RabbitMqExpectedListOfMessagesExtension.class)
public @interface EnableRabbitMqTest {
}
