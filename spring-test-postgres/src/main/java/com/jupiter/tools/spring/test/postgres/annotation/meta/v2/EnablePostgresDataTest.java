package com.jupiter.tools.spring.test.postgres.annotation.meta.v2;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.jpa.annotation.EnableDataTest;
import com.jupiter.tools.spring.test.postgres.annotation.EnableSpringRider;
import com.jupiter.tools.spring.test.postgres.annotation.meta.v2.properties.DefaultSqlSettings;

/**
 * Created on 26/02/2020
 * <p>
 * Meta-annotation to start the Postgres container
 * and DataJPA context in tests
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DefaultSqlSettings
@EnableDataTest
@EnableSpringRider
@EnablePostgresTestContainers
public @interface EnablePostgresDataTest {
}
