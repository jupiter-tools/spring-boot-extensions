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

    /**
     * The set of prefixes to the spring datasource properties,
     * each value will be started a separate container of Postgres.
     *
     * Default value is 'spring.datasource'
     */
    private Set<String> dataSourcePropertiesMapping;

    public PgContextCustomizer(Set<String> value){
        this.dataSourcePropertiesMapping = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        PgContextCustomizer that = (PgContextCustomizer) o;
        return Objects.equals(dataSourcePropertiesMapping, that.dataSourcePropertiesMapping);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSourcePropertiesMapping);
    }



    @Override
    public void customizeContext(ConfigurableApplicationContext configurableApplicationContext,
                                 MergedContextConfiguration mergedContextConfiguration) {


        if(dataSourcePropertiesMapping == null || dataSourcePropertiesMapping.isEmpty()){
            return;
        }

        for(String dataSourcePrefix : dataSourcePropertiesMapping) {

            log.info("Try to start PostgreSQL TestContainer -> {}",dataSourcePrefix);
            PostgreSQLContainer postgres = new PostgreSQLContainer();
            postgres.start();
            log.info("Started PostgreSQL TestContainer at:[{}]", postgres.getJdbcUrl());

            String dialect = PostgreSQL9Dialect.class.getCanonicalName();

            TestPropertyValues testPropertyValues =
                    TestPropertyValues.of("${dataSourcePrefix}.driver-class-name=${postgres.getDriverClassName()}",
                                          "${dataSourcePrefix}.url=${postgres.getJdbcUrl()}",
                                          "${dataSourcePrefix}.username=${postgres.getUsername()}",
                                          "${dataSourcePrefix}.password=${postgres.getPassword()}",
                                          "spring.jpa.properties.hibernate.dialect=${dialect}");

            testPropertyValues.applyTo(configurableApplicationContext);
        }
    }
}
