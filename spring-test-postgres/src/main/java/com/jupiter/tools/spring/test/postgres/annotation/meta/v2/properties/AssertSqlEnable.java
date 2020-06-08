package com.jupiter.tools.spring.test.postgres.annotation.meta.v2.properties;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.TestPropertySource;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.session_factory.statement_inspector=" +
        "com.jupiter.tools.spring.test.jpa.tracesql.StatementInspectorImpl"
})
public @interface AssertSqlEnable {
}
