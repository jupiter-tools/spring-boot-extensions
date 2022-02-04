package com.jupiter.tools.spring.test.mssql.annotation.meta;


import com.jupiter.tools.spring.test.core.annotation.EnableIntegrationTest;
import com.jupiter.tools.spring.test.jpa.annotation.EnableRiderTest;
import com.jupiter.tools.spring.test.mssql.annotation.EnableMsSqlTestContainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 19.07.2018.
 *
 * Meta-annotation for the combination of: IntegrationTests, RiderTests
 * and MSSQL docker container.
 *
 * @author Korovin Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableIntegrationTest
@EnableRiderTest
@EnableMsSqlTestContainers
public @interface EnableMsSqlIntegrationTest {
}
