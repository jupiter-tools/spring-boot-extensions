package com.jupiter.tools.spring.test.activemq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.activemq.extension.ExpectedMessagesExtension;
import org.junit.jupiter.api.extension.ExtendWith;

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
     * The timeout for wait messages after the test execution
     */
    int timeout() default 3000;

    /**
     * The path to the JSON file with expected messages
     */
    String messagesFile();

    /**
     * Ignore the receiving of unexpected messages (if true)
     */
    boolean ignoreUnexpected() default false;
}
