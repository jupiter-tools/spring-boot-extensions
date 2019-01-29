package com.jupiter.tools.spring.test.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.core.annotation.EnableIntegrationTest;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 17.07.2018.
 *
 * Enable configuration for tests with an API level.
 * Run entire application context and configure a MockMvc.
 *
 * Main test-cases:
 * - test REST API communication
 * - test API response/request converters
 * - test all application scoup from REST-API (for example: API->Service->DAO->JPA)
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableIntegrationTest
@AutoConfigureMockMvc
public @interface EnableRestTest {
}
