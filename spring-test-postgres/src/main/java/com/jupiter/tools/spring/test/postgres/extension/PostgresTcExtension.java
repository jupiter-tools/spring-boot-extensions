package com.jupiter.tools.spring.test.postgres.extension;

import org.hibernate.dialect.PostgreSQL9Dialect;
import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Created on 06.08.2018.
 *
 * @author Korovin Anatoliy
 */
public class PostgresTcExtension implements Extension {

    static {
        System.out.println("Start Postgres testcontainers extension...\n");

        PostgreSQLContainer postgres = new PostgreSQLContainer();
        postgres.start();

        System.setProperty("spring.datasource.driver-class-name", postgres.getDriverClassName());
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
        System.setProperty("spring.jpa.properties.hibernate.dialect", PostgreSQL9Dialect.class.getCanonicalName());
    }
}
