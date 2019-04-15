package com.jupiter.tools.spring.test.jpa.extension;

import com.jupiter.tools.spring.test.jpa.annotation.ExpectedSqlQuery;
import com.jupiter.tools.spring.test.jpa.tracesql.AssertSqlCount;
import com.jupiter.tools.spring.test.jpa.tracesql.AssertSqlQueryCount;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Created on 15.04.2019.
 *
 * TODO: replace on javadoc
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

        ExpectedSqlQuery expectedSqlQuery = context.getRequiredTestMethod()
                                                   .getAnnotation(ExpectedSqlQuery.class);

        AssertSqlQueryCount.assertCount(expectedSqlQuery.type(), expectedSqlQuery.count());
    }
}
