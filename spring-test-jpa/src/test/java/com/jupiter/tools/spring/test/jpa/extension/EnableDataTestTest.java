package com.jupiter.tools.spring.test.jpa.extension;

import com.jupiter.tools.spring.test.jpa.annotation.EnableDataTest;
import com.jupiter.tools.spring.test.jpa.tracesql.AssertSqlQueryCount;
import com.jupiter.tools.spring.test.jpa.tracesql.QueryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 26.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableDataTest
@ExtendWith(TraceSqlExtension.class)
@Import(TransactionalTestConfig.class)
class EnableDataTestTest {

    @Autowired
    private TransactionalTestConfig.FooRepository fooRepository;

    @BeforeEach
    void setUp() {
        AssertSqlQueryCount.reset();
    }

    @Test
    @Commit
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void testInsert() {
        // Act
        fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));
        // Assert
        AssertSqlQueryCount.assertCount(QueryType.INSERT,1);
    }
}
