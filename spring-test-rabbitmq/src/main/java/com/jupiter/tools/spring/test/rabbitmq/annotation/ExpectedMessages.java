package com.jupiter.tools.spring.test.rabbitmq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 22.01.2019.
 *
 * By the use of this annotation, you can declare a list of
 * expected messages in JSON format, which you expect after the test execution.
 * Also, you can set a timeout, which will wait.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExpectedMessages {

    /**
     * The name of a queue for messages
     */
    String queue();

    /**
     * The timeout for wait messages after the test execution (10 sec. by default)
     */
    int timeout() default 10000;

    /**
     * The path to the JSON file with expected messages
     */
    String messagesFile();
}
