package com.jupiter.tools.spring.test.jpa.tracesql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 27.01.2019.
 *
 * @author Korovin Anatoliy
 */
class QueryCountInfoHandlerTest {

    private static Stream<TestArg> params() {
        return Stream.of(new TestArg("insert ...", QueryCountInfo::getInsertCount),
                         new TestArg("select ...", QueryCountInfo::getSelectCount),
                         new TestArg("update ...", QueryCountInfo::getUpdateCount),
                         new TestArg("delete ...", QueryCountInfo::getDeleteCount),
                         new TestArg("call ...", QueryCountInfo::getCallCount),
                         new TestArg("oops ...", QueryCountInfo::getOtherCount));
    }

    @ParameterizedTest
    @MethodSource("params")
    void handleOp(TestArg arg) {
        // Arrange
        QueryCountInfoHolder.getQueryInfo().clear();
        QueryCountInfoHandler handler = new QueryCountInfoHandler();
        // Act
        handler.handleSql(arg.getRequest());
        // Asserts
        int count = arg.getRetrieveCount().apply(QueryCountInfoHolder.getQueryInfo());
        assertThat(count).isEqualTo(1);
    }

    @Getter
    @AllArgsConstructor
    static class TestArg {
        String request;
        Function<QueryCountInfo, Integer> retrieveCount;
    }
}