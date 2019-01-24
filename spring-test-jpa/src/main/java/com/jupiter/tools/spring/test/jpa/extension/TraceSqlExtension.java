package com.jupiter.tools.spring.test.jpa.extension;

import com.jupiter.tools.spring.test.jpa.tracesql.StatementInspectorImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.extension.Extension;

/**
 * Created on 07.08.2018.
 *
 * @author Korovin Anatoliy
 */
public class TraceSqlExtension implements Extension {

    static {
        setUpDbConnectionSetting();
        setUpCustomStatementInspector();
        setUpTraceProperties();
    }

    private static void setUpDbConnectionSetting() {
        System.setProperty("spring.datasource.type", HikariDataSource.class.getCanonicalName());
        System.setProperty("spring.jpa.generate-ddl", "true");
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        System.setProperty("spring.jpa.show-sql", "true");
        System.setProperty("spring.jpa.properties.hibernate.hbm2ddl.auto", "create-drop");
    }

    private static void setUpCustomStatementInspector() {
        System.setProperty("spring.jpa.properties.hibernate.session_factory.statement_inspector",
                           StatementInspectorImpl.class.getCanonicalName());
    }


    private static void setUpTraceProperties() {
        System.setProperty("spring.jpa.show-sql", "true");
        System.setProperty("spring.jpa.properties.hibernate.type", "trace");
        System.setProperty("spring.jpa.properties.hibernate.format_sql", "true");
        System.setProperty("spring.jpa.properties.hibernate.use_sql_comments", "true");
        System.setProperty("logging.level.org.hibernate.type.descriptor.sql", "trace");
    }
}
