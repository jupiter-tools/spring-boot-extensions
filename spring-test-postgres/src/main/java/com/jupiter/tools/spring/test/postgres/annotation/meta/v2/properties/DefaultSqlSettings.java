package com.jupiter.tools.spring.test.postgres.annotation.meta.v2.properties;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TraceSql
@DefaultHibernateProperties
@AssertSqlEnable
public @interface DefaultSqlSettings {
}
