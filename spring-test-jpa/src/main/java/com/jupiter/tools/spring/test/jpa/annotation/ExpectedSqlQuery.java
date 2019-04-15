package com.jupiter.tools.spring.test.jpa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.jpa.extension.ExpectedSqlQueryExtension;
import com.jupiter.tools.spring.test.jpa.extension.TraceSqlExtension;
import com.jupiter.tools.spring.test.jpa.tracesql.QueryType;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Created on 15.04.2019.
 *
 * Assert count of executed queries after the test execution
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(TraceSqlExtension.class)
@ExtendWith(ExpectedSqlQueryExtension.class)
@Repeatable(ExpectedSqlQueries.class)
public @interface ExpectedSqlQuery {

    QueryType type();

    int count();
}
