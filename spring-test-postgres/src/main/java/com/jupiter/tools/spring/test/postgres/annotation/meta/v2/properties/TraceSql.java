package com.jupiter.tools.spring.test.postgres.annotation.meta.v2.properties;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.TestPropertySource;



@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TestPropertySource(properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.properties.hibernate.use_sql_comments=true",
        "spring.jpa.properties.hibernate.type=TRACE",
        "logging.level.org.hibernate.type.descriptor.sql=TRACE",
        "logging.level.org.springframework.jdbc.datasource.init.ScriptUtils=TRACE",
        "logging.level.org.hibernate.event.internal=TRACE"
})
public @interface TraceSql {

}
