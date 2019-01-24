package com.jupiter.tools.spring.test.mysql.extension;

import org.hibernate.dialect.MySQL5Dialect;
import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MySQLContainer;

/**
 * Created on 07.08.2018.
 *
 * @author Korovin Anatoliy
 */
public class MySqlTcExtension implements Extension {

    static {
        System.out.println("Start MySql testcontainers extension...\n");

        MySQLContainer mysql = new MySQLContainer();
        mysql.start();

        System.setProperty("spring.datasource.driver-class-name", mysql.getDriverClassName());
        System.setProperty("spring.datasource.url", mysql.getJdbcUrl() + "?useSSL=false");
        System.setProperty("spring.datasource.username", mysql.getUsername());
        System.setProperty("spring.datasource.password", mysql.getPassword());
        System.setProperty("spring.jpa.properties.hibernate.dialect", MySQL5Dialect.class.getCanonicalName());
    }
}
