package com.jupiter.tools.spring.test.mssql.annotation;

import com.jupiter.tools.spring.test.jpa.extension.TraceSqlExtension;
import com.jupiter.tools.spring.test.mssql.extension.MsSqlTcExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 17.07.2018.
 * <p>
 * Start a MSSQL container in Docker, by a test-containers library.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(MsSqlTcExtension.class)
@ExtendWith(TraceSqlExtension.class)
@ActiveProfiles("jupiter-tools.spring-test-mssql.test-containers")
public @interface EnableMsSqlTestContainers {
}
