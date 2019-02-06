package com.jupiter.tools.spring.test.web.extension.ribbon;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

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
@ExtendWith(RedirectRibbonExtension.class)
@RibbonClients(defaultConfiguration = RedirectRibbonExtensionConfig.class)
public @interface RedirectRibbonToEmbeddedWebServer {

    /**
     * @return List of client names which should be redirected to the embedded web server
     */
    String[] value() default {};
}
