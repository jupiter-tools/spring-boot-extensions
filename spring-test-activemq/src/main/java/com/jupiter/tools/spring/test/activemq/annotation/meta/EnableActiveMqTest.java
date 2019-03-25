package com.jupiter.tools.spring.test.activemq.annotation.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.activemq.annotation.EnableActiveMqTestContainers;
import com.jupiter.tools.spring.test.activemq.extension.ActiveMqMessageExtension;
import com.jupiter.tools.spring.test.activemq.extension.ActiveMqTcExtension;
import com.jupiter.tools.spring.test.activemq.extension.ExpectedMessagesExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 28.01.2019.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableActiveMqTestContainers
@ExtendWith(ActiveMqMessageExtension.class)
@ExtendWith(ExpectedMessagesExtension.class)
public @interface EnableActiveMqTest {
}
