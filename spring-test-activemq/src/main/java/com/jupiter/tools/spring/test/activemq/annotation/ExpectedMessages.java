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
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExpectedMessages {

    String queue();

    int timeout() default 3000;

    String messagesFile();

    boolean ignoreUnexpected() default false;
}
