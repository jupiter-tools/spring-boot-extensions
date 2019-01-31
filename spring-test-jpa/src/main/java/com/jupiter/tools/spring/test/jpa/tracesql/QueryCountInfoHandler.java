package com.jupiter.tools.spring.test.jpa.tracesql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 12/6/15
 *
 * @author Igor Dmitriev
 * @author Mikalai Alimenkou
 * @author Korovin Anatoliy
 */
public class QueryCountInfoHandler implements QueryHandler {

    private static Logger log = LoggerFactory.getLogger(QueryCountInfoHandler.class);

    @Override
    public void handleSql(String sql) {
        QueryType queryType = getQueryType(sql);
        QueryCountInfo queryCountInfo = QueryCountInfoHolder.getQueryInfo();
        log.debug("assertSql.handle({})", queryType);
        switch (queryType) {
            case SELECT:
                queryCountInfo.incrementSelectCount();
                break;
            case INSERT:
                queryCountInfo.incrementInsertCount();
                break;
            case UPDATE:
                queryCountInfo.incrementUpdateCount();
                break;
            case DELETE:
                queryCountInfo.incrementDeleteCount();
                break;
            case CALL:
                queryCountInfo.incrementCallCount();
                break;
            case OTHER:
                queryCountInfo.incrementOtherCount();
                break;
            default:
                throw new IllegalArgumentException("There is no QueryType handler:" + queryType);
        }
    }

    protected QueryType getQueryType(String query) {
        query = query.toLowerCase();
        final String trimmedQuery = removeRedundantSymbols(query);
        final char firstChar = trimmedQuery.charAt(0);

        final QueryType type;
        switch (firstChar) {
            case 'w': // query can be started 'with'
            case 's':
                type = QueryType.SELECT;
                break;
            case 'i':
                type = QueryType.INSERT;
                break;
            case 'u':
                type = QueryType.UPDATE;
                break;
            case 'd':
                type = QueryType.DELETE;
                break;
            case 'c':
            case '?':
                type = QueryType.CALL;
                break;
            default:
                log.warn("Unknown QueryType handle by StatementInspector: "+query);
                type = QueryType.OTHER;
        }
        return type;
    }

    private String removeRedundantSymbols(String query) {
        return query.replaceAll("--.*\n", "")
                    .replaceAll("\n", "")
                    .replaceAll("/\\*.*\\*/", "")
                    .trim();
    }
}
