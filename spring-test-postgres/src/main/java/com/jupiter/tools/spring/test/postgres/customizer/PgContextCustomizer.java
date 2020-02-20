package com.jupiter.tools.spring.test.postgres.customizer;

import java.util.Objects;
import java.util.Set;

import org.hibernate.dialect.PostgreSQL9Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;

/**
 * Created on 20/02/2020
 * <p>
 * TODO: replace on the JavaDoc
 *
 * @author Korovin Anatoliy
 */
public class PgContextCustomizer implements ContextCustomizer {

    private static final Logger log = LoggerFactory.getLogger(PgContextCustomizer.class);

    private Set<String> value;

    public PgContextCustomizer(Set<String> value){
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        PgContextCustomizer that = (PgContextCustomizer) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }



    @Override
    public void customizeContext(ConfigurableApplicationContext configurableApplicationContext,
                                 MergedContextConfiguration mergedContextConfiguration) {


        if(value==null || value.isEmpty()){
            return;
        }

        log.debug("Try to start PostgreSQL TestContainer");
        PostgreSQLContainer postgres = new PostgreSQLContainer();
        postgres.start();

        log.debug("Started PostgreSQL TestContainer at:[{}]", postgres.getJdbcUrl());


        String dialect = PostgreSQL9Dialect.class.getCanonicalName();

        TestPropertyValues testPropertyValues =
                TestPropertyValues.of("spring.datasource.driver-class-name=${postgres.getDriverClassName()}",
                                      "spring.datasource.url=${postgres.getJdbcUrl()}",
                                      "spring.datasource.username=${postgres.getUsername()}",
                                      "spring.datasource.password=${postgres.getPassword()}",
                                      "spring.jpa.properties.hibernate.dialect=${dialect}");

        testPropertyValues.applyTo(configurableApplicationContext);
    }
}
