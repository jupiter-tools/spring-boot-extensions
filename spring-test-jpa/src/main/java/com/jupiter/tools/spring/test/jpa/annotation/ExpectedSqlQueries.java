package com.jupiter.tools.spring.test.jpa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.jpa.extension.ExpectedSqlQueryExtension;
import com.jupiter.tools.spring.test.jpa.extension.TraceSqlExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created on 15.04.2019.
 * @author Korovin Anatoliy
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(TraceSqlExtension.class)
@ExtendWith(ExpectedSqlQueryExtension.class)
public @interface ExpectedSqlQueries {

    ExpectedSqlQuery[] value();
}
