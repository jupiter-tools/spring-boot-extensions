package com.jupiter.tools.spring.test.jpa.tracesql;

/**
 * @author Igor Dmitriev
 * @author Mikalai Alimenkou
 * @author Korovin Anatoliy
 */
public class QueryCountInfoHolder {

    private static final ThreadLocal<QueryCountInfo> QUERY_INFO_HOLDER =
            ThreadLocal.withInitial(() -> new QueryCountInfo());

    public static QueryCountInfo getQueryInfo() {
        return QUERY_INFO_HOLDER.get();
    }
}
