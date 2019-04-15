package com.jupiter.tools.spring.test.jpa.tracesql;

import com.jupiter.tools.spring.test.jpa.tracesql.exceptions.SqlQueryCountException;
import com.jupiter.tools.spring.test.jpa.tracesql.exceptions.UndefinedSqlQueryTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jupiter.tools.spring.test.jpa.tracesql.QueryCountInfoHolder.getQueryInfo;

/**
 * Created on 15.04.2019.
 *
 * TODO: replace on javadoc
 *
 * @author Korovin Anatoliy
 */
public class AssertSqlQueryCount {

    private static Logger log = LoggerFactory.getLogger(AssertSqlQueryCount.class);

    public static void reset() {
        log.debug("assertSql.reset()");
        getQueryInfo().clear();
    }

    public static void assertCount(QueryType queryType, int expectedCount) {
        assertSqlCount(queryType, expectedCount, getActualQueryCount(queryType));
    }

    private static int getActualQueryCount(QueryType queryType) {
        switch (queryType) {
            case CALL:
                return getQueryInfo().getCallCount();
            case SELECT:
                return getQueryInfo().getSelectCount();
            case DELETE:
                return getQueryInfo().getDeleteCount();
            case INSERT:
                return getQueryInfo().getInsertCount();
            case UPDATE:
                return getQueryInfo().getUpdateCount();
            case OTHER:
                return getQueryInfo().getOtherCount();
            default:
                throw new UndefinedSqlQueryTypeException("Undefined SQL query type:" + queryType);
        }
    }

    private static void assertSqlCount(QueryType queryType, int expectedCount, int actualCount) {
        log.debug("assertSql.assert({}, {}, {})", queryType, expectedCount, actualCount);
        if (expectedCount != actualCount) {
            throw new SqlQueryCountException(queryType, expectedCount, actualCount);
        }
    }
}
