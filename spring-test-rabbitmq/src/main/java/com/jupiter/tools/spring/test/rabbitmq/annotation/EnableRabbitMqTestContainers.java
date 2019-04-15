package com.jupiter.tools.spring.test.rabbitmq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.rabbitmq.extension.RabbitMqTcExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created on 28.01.2019.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(RabbitMqTcExtension.class)
@ActiveProfiles("jupiter-tools.spring-test-rabbitmq.test-containers")
public @interface EnableRabbitMqTestContainers {
}
