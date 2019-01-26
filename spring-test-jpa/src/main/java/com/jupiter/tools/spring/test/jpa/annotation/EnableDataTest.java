package com.jupiter.tools.spring.test.jpa.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 17.07.2018.
 *
 * Enable a configuration for the test with a database specific.
 * Use this only for testing DAO level.
 * Does not load entire context configuration.
 *
 * Main test cases:
 * - testing db.schem and constraints
 * - testing queries created by the spring-data, specification, querydsl
 * - testing custom query
 * - asserts sql query count
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public @interface EnableDataTest {
}
