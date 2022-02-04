package com.jupiter.tools.spring.test.mssql.annotation.meta;

import com.jupiter.tools.spring.test.jpa.annotation.EnableDataTest;
import com.jupiter.tools.spring.test.jpa.annotation.EnableRiderTest;
import com.jupiter.tools.spring.test.mssql.annotation.EnableMsSqlTestContainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 19.07.2018.
 *
 * Meta-annotation for the combination of: DataTests, RiderTests
 * and MSSQL docker container.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableDataTest
@EnableRiderTest
@EnableMsSqlTestContainers
public @interface EnableMsSqlDataTest {
}
