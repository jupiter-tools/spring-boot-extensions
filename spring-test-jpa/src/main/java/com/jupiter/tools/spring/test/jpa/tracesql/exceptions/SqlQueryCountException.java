package com.jupiter.tools.spring.test.jpa.tracesql.exceptions;

import com.jupiter.tools.spring.test.jpa.tracesql.QueryType;

/**
 * Created on 15.04.2019.
 * @author Korovin Anatoliy
 */
public class SqlQueryCountException extends RuntimeException {

    public SqlQueryCountException(QueryType queryType, int expectedCount, int actualCount) {
        super(String.format("Expected query: %s, count: %d, but actual count: %d",
                            queryType,
                            expectedCount,
                            actualCount));
    }
}
