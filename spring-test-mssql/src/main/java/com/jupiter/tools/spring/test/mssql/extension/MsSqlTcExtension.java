package com.jupiter.tools.spring.test.mssql.extension;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MSSQLServerContainer;

/**
 * Created on 06.08.2018.
 *
 * @author Korovin Anatoliy
 */
public class MsSqlTcExtension implements Extension {

    static {
        System.out.println("Start MsSql testcontainers extension...\n");

        MSSQLServerContainer mssql = new MSSQLServerContainer("mcr.microsoft.com/mssql/server:2022-latest")
                .acceptLicense();
        mssql.start();

        System.setProperty("spring.datasource.driver-class-name", mssql.getDriverClassName());
        System.setProperty("spring.datasource.url", mssql.getJdbcUrl());
        System.setProperty("spring.datasource.username", mssql.getUsername());
        System.setProperty("spring.datasource.password", mssql.getPassword());
        System.setProperty("spring.jpa.properties.hibernate.dialect", SQLServer2012Dialect.class.getCanonicalName());
    }
}
