package com.jupiter.tools.spring.test.jpa.tracesql;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created on 27.01.2019.
 *
 * @author Korovin Anatoliy
 */
class QueryCountInfoTest {

    @Test
    void incrementSelectCount() {
        // Arrange
        QueryCountInfo queryCountInfo = new QueryCountInfo();
        assertThat(queryCountInfo.getSelectCount()).isEqualTo(0);
        // Act
        queryCountInfo.incrementSelectCount();
        // Asserts
        assertThat(queryCountInfo.getSelectCount()).isEqualTo(1);
    }

    @Test
    void incrementInsertCount() {
        // Arrange
        QueryCountInfo queryCountInfo = new QueryCountInfo();
        assertThat(queryCountInfo.getInsertCount()).isEqualTo(0);
        // Act
        queryCountInfo.incrementInsertCount();
        // Asserts
        assertThat(queryCountInfo.getInsertCount()).isEqualTo(1);
    }

    @Test
    void incrementUpdateCount() {
        // Arrange
        QueryCountInfo queryCountInfo = new QueryCountInfo();
        assertThat(queryCountInfo.getUpdateCount()).isEqualTo(0);
        // Act
        queryCountInfo.incrementUpdateCount();
        // Asserts
        assertThat(queryCountInfo.getUpdateCount()).isEqualTo(1);
    }

    @Test
    void incrementDeleteCount() {
        // Arrange
        QueryCountInfo queryCountInfo = new QueryCountInfo();
        assertThat(queryCountInfo.getDeleteCount()).isEqualTo(0);
        // Act
        queryCountInfo.incrementDeleteCount();
        // Asserts
        assertThat(queryCountInfo.getDeleteCount()).isEqualTo(1);
    }

    @Test
    void incrementCallCount() {
        // Arrange
        QueryCountInfo queryCountInfo = new QueryCountInfo();
        assertThat(queryCountInfo.getCallCount()).isEqualTo(0);
        // Act
        queryCountInfo.incrementCallCount();
        // Asserts
        assertThat(queryCountInfo.getCallCount()).isEqualTo(1);
    }

    @Test
    void incrementOtherCount() {
        // Arrange
        QueryCountInfo queryCountInfo = new QueryCountInfo();
        assertThat(queryCountInfo.getOtherCount()).isEqualTo(0);
        // Act
        queryCountInfo.incrementOtherCount();
        // Asserts
        assertThat(queryCountInfo.getOtherCount()).isEqualTo(1);
    }

    @Test
    void clear() {
        // Arrange
        QueryCountInfo queryCountInfo = new QueryCountInfo();
        queryCountInfo.incrementInsertCount();
        queryCountInfo.incrementOtherCount();
        queryCountInfo.incrementCallCount();
        queryCountInfo.incrementDeleteCount();
        queryCountInfo.incrementUpdateCount();
        queryCountInfo.incrementSelectCount();
        // Act
        queryCountInfo.clear();
        // Asserts
        assertThat(queryCountInfo.getCallCount()).isEqualTo(0);
        assertThat(queryCountInfo.getOtherCount()).isEqualTo(0);
        assertThat(queryCountInfo.getDeleteCount()).isEqualTo(0);
        assertThat(queryCountInfo.getUpdateCount()).isEqualTo(0);
        assertThat(queryCountInfo.getInsertCount()).isEqualTo(0);
        assertThat(queryCountInfo.getSelectCount()).isEqualTo(0);
    }
}