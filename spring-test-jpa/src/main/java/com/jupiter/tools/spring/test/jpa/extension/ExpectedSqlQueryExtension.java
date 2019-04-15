package com.jupiter.tools.spring.test.jpa.extension;

import com.jupiter.tools.spring.test.jpa.annotation.ExpectedSqlQueries;
import com.jupiter.tools.spring.test.jpa.annotation.ExpectedSqlQuery;
import com.jupiter.tools.spring.test.jpa.tracesql.AssertSqlQueryCount;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Created on 15.04.2019.
 *
 * Extension to process the expected count of executed sql queries
 * after a test execution
 *
 * @author Korovin Anatoliy
 */
public class ExpectedSqlQueryExtension implements Extension,
                                                  BeforeEachCallback,
                                                  AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AssertSqlQueryCount.reset();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        // processing the usage of repeatable annotations
        ExpectedSqlQueries sqlQueries = context.getRequiredTestMethod()
                                               .getAnnotation(ExpectedSqlQueries.class);
        if (sqlQueries != null) {
            for (ExpectedSqlQuery sqlQuery : sqlQueries.value()) {
                AssertSqlQueryCount.assertCount(sqlQuery.type(), sqlQuery.count());
            }
        }

        // processing the usage of a single annotation
        ExpectedSqlQuery expectedSqlQuery = context.getRequiredTestMethod()
                                                   .getAnnotation(ExpectedSqlQuery.class);
        if (expectedSqlQuery != null) {
            AssertSqlQueryCount.assertCount(expectedSqlQuery.type(), expectedSqlQuery.count());
        }
    }
}
