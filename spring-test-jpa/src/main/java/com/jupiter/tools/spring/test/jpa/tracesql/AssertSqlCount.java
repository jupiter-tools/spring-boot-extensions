package com.jupiter.tools.spring.test.jpa.tracesql;


import lombok.extern.slf4j.Slf4j;

import static com.jupiter.tools.spring.test.jpa.tracesql.QueryCountInfoHolder.getQueryInfo;


/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 12/6/15
 */
@Slf4j
public class AssertSqlCount {

    public static void reset() {
        log.debug("assertSql.reset()");
        getQueryInfo().clear();
    }

    public static void assertSelectCount(int expectedSelectCount) {
        assertSqlCount("select", expectedSelectCount, getQueryInfo().getSelectCount());
    }

    public static void assertUpdateCount(int expectedUpdateCount) {
        assertSqlCount("update", expectedUpdateCount, getQueryInfo().getUpdateCount());
    }

    public static void assertInsertCount(int expectedInsertCount) {
        assertSqlCount("insert", expectedInsertCount, getQueryInfo().getInsertCount());
    }

    public static void assertDeleteCount(int expectedDeleteCount) {
        assertSqlCount("delete", expectedDeleteCount, getQueryInfo().getDeleteCount());

    }

    public static void assertCallCount(int expectedCallCount) {
        assertSqlCount("call", expectedCallCount, getQueryInfo().getCallCount());
    }

    private static void assertSqlCount(String statement, int expectedCount, int actualCount) {
        log.debug("assertSql.assert({}, {}, {})", statement, expectedCount, actualCount);
        if (expectedCount != actualCount) {
            throw new SqlCountMismatchException(statement, expectedCount, actualCount);
        }
    }
}
