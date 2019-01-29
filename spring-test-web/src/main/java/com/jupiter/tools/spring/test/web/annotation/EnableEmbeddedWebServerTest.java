package com.jupiter.tools.spring.test.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.web.extension.RandomPortBindingExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 29.08.2018.
 *
 * Change the server.port property to available TCP port,
 * than start web environment for this port
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@ExtendWith(RandomPortBindingExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public @interface EnableEmbeddedWebServerTest {
}
