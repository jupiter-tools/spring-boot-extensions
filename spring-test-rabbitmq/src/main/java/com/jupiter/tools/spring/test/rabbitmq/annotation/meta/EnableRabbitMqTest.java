package com.jupiter.tools.spring.test.rabbitmq.annotation.meta;

import com.jupiter.tools.spring.test.rabbitmq.annotation.EnableRabbitMqTestContainers;
import com.jupiter.tools.spring.test.rabbitmq.extension.ExpectedMessagesExtension;
import com.jupiter.tools.spring.test.rabbitmq.extension.RabbitMqMessageExtension;
import com.jupiter.tools.spring.test.rabbitmq.extension.RabbitMqTcExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
@ExtendWith(ExpectedMessagesExtension.class)
public @interface EnableRabbitMqTest {
}
