package com.jupiter.tools.spring.test.mysql.annotation;

import com.jupiter.tools.spring.test.jpa.extension.TraceSqlExtension;
import com.jupiter.tools.spring.test.mysql.extension.MySqlTcExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 18.07.2018.
 * <p>
 * Start a MySQL container in Docker, by the test-containers library.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(MySqlTcExtension.class)
@ExtendWith(TraceSqlExtension.class)
public @interface EnableMySqlTestContainers {
}
