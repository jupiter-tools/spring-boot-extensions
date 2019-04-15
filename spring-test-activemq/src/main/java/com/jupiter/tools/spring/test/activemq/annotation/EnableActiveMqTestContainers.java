package com.jupiter.tools.spring.test.activemq.annotation;

import com.jupiter.tools.spring.test.activemq.extension.ActiveMqTcExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 28.01.2019.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(ActiveMqTcExtension.class)
@ActiveProfiles("jupiter-tools.spring-test-activemq.test-containers")
public @interface EnableActiveMqTestContainers {
}
