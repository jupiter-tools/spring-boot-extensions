package com.jupiter.tools.spring.test.postgres.annotation;

import com.jupiter.tools.spring.test.jpa.extension.TraceSqlExtension;
import com.jupiter.tools.spring.test.postgres.extension.PostgresTcExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 17.07.2018.
 * <p>
 * Start a PostgreSQL container in Docker, by a test-containers library.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(PostgresTcExtension.class)
@ExtendWith(TraceSqlExtension.class)
public @interface EnablePostgresTestContainers {
}
