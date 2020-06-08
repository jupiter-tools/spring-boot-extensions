package com.jupiter.tools.spring.test.postgres.annotation.meta.v2;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.core.annotation.EnableIntegrationTest;
import com.jupiter.tools.spring.test.postgres.annotation.EnableSpringRider;
import com.jupiter.tools.spring.test.postgres.annotation.meta.v2.properties.DefaultSqlSettings;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DefaultSqlSettings
@EnableIntegrationTest
@EnableSpringRider
@EnablePostgresTestContainers
public @interface EnablePostgresIntegrationTest {
}
