package com.jupiter.tools.spring.test.jpa.extension;

import com.jupiter.tools.spring.test.core.annotation.EnableIntegrationTest;
import com.jupiter.tools.spring.test.jpa.tracesql.AssertSqlCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

/**
 * Created on 26.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableIntegrationTest
@ExtendWith(TraceSqlExtension.class)
@Import(TransactionalTestConfig.class)
class EnableIntegrationTestTest {

    @Autowired
    private TransactionalTestConfig.TestService testService;

    @BeforeEach
    void setUp() {
        AssertSqlCount.reset();
    }

    @Test
    void testInsert() {
        // Act
        testService.ok();
        // Assert
        AssertSqlCount.assertInsertCount(1);
    }
}
