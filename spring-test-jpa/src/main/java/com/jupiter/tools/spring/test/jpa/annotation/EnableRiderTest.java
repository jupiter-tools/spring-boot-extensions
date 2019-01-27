package com.jupiter.tools.spring.test.jpa.annotation;

import com.github.database.rider.spring.api.DBRider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 17.07.2018.
 *
 * Enable a configuration for the writing tests with
 * DatabaseRider library.
 *
 * You can combine this configuration with
 * EnableIntegrationTests  or  EnableDataTests.
 *
 * In order to work with a real DataBase in tests, you can use
 * this annotation in combination with some of TestContainersConfiguration:
 * - EnablePostgresTestContainers
 * - EnableMySqlTestContainers
 * By default use a configuration with H2
 *
 *
 * Main test-cases:
 * - init DataSet
 * - check expected DataSets after test
 * - generate DataSet
 *
 * @author Korovin Anatoliy
 */
@DBRider
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableRiderTest {
}
