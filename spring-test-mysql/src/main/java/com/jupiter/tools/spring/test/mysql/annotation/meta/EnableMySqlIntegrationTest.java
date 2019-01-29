package com.jupiter.tools.spring.test.mysql.annotation.meta;

import com.jupiter.tools.spring.test.core.annotation.EnableIntegrationTest;
import com.jupiter.tools.spring.test.jpa.annotation.EnableRiderTest;
import com.jupiter.tools.spring.test.mysql.annotation.EnableMySqlTestContainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 19.07.2018.
 *
 * Meta-annotation for the combination of:
 * IntegrationTests, RiderTests and MySQL docker-container
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableIntegrationTest
@EnableRiderTest
@EnableMySqlTestContainers
public @interface EnableMySqlIntegrationTest {
}
