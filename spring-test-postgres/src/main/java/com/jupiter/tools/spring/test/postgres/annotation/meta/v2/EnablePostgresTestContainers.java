package com.jupiter.tools.spring.test.postgres.annotation.meta.v2;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.postgres.customizer.PostgresTestContainer;

/**
 * Created on 26/02/2020
 * <p>
 * Start the PostgreSQL container in Docker, by the test-containers library.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@PostgresTestContainer
public @interface EnablePostgresTestContainers {
}
