package com.jupiter.tools.spring.test.jpa.tracesql;

/**
 * @author Igor Dmitriev
 * @author Mikalai Alimenkou
 * @author Korovin Anatoliy
 */
public interface QueryHandler {
    void handleSql(String sql);
}
