package com.jupiter.tools.spring.test.postgres.extension;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.Properties;

/**
 * Created on 06.08.2018.
 *
 * @author Korovin Anatoliy
 */
public class PostgresTcExtension implements Extension {

    static {
        System.out.println("Start Postgres testcontainers extension...\n");

        PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:" + getDockerImageName());
        postgres.start();

        System.setProperty("spring.datasource.driver-class-name", postgres.getDriverClassName());
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
        System.setProperty("spring.jpa.properties.hibernate.dialect", PostgreSQL10Dialect.class.getCanonicalName());
    }

    private static String getDockerImageName() {
        try {
            Properties props = new Properties();
            props.load(PostgresTcExtension.class.getResourceAsStream("/tc-extension.properties"));
            return props.getProperty("tc-extension.postgres.docker-image-version", "latest");
        } catch (Exception e) {
            e.printStackTrace();
            return "latest";
        }
    }

}
