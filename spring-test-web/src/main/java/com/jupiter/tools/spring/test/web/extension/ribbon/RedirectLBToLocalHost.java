package com.jupiter.tools.spring.test.web.extension.ribbon;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 05.02.2019.
 *
 * @author Korovin Anatoliy
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(RedirectLBExtension.class)
@Import(RedirectLBExtensionConfig.class)
public @interface RedirectLBToLocalHost {

    /**
     * @return List of client names which should be redirected to the embedded web server
     */
    String[] value() default {};
}
