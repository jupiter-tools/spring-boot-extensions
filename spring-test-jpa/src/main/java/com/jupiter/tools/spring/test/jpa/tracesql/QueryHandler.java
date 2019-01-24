package com.jupiter.tools.spring.test.jpa.tracesql;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 12/6/15
 */
public interface QueryHandler {
    void handleSql(String sql);
}
