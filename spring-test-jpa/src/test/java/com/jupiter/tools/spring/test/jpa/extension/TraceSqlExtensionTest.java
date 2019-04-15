package com.jupiter.tools.spring.test.jpa.extension;

import com.jupiter.tools.spring.test.jpa.tracesql.AssertSqlQueryCount;
import com.jupiter.tools.spring.test.jpa.tracesql.QueryType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 26.01.2019.
 *
 * @author Korovin Anatoliy
 */
@ExtendWith(TraceSqlExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(TransactionalTestConfig.class)
class TraceSqlExtensionTest {

    @Autowired
    private TransactionalTestConfig.FooRepository fooRepository;

    @BeforeEach
    void setUp() {
        fooRepository.deleteAll();
        AssertSqlQueryCount.reset();
    }

    @Test
    void testInsert() {
        // Act
        fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));
        // Assert
        AssertSqlQueryCount.assertCount(QueryType.INSERT, 1);
    }

    @Test
    void testSelect() {
        // Act
        fooRepository.findAll();
        // Asserts
        AssertSqlQueryCount.assertCount(QueryType.SELECT, 1);
    }

    @Test
    void testUpdate() {
        // Arrange
        TransactionalTestConfig.Foo foo =
                fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));

        foo.setField("up");
        // Act
        fooRepository.save(foo);
        // Asserts
        AssertSqlQueryCount.assertCount(QueryType.UPDATE, 1);
    }

    @Test
    void testDelete() {
        // Arrange
        fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));
        // Act
        fooRepository.deleteAll();
        // Asserts
        AssertSqlQueryCount.assertCount(QueryType.DELETE,1);
    }

    @Test
    void testCall() {
        // Act
        fooRepository.rand();
        // Asserts
        AssertSqlQueryCount.assertCount(QueryType.CALL, 1);
    }

    @Test
    void testCallSeq() {
        // Arrange
        Set<Long> rands = new HashSet<>();
        // Act
        for (int i = 0; i < 10; i++) {
            rands.add(fooRepository.rand());
        }
        // Asserts
        assertThat(rands.size() > 3).isTrue();
        AssertSqlQueryCount.assertCount(QueryType.CALL, 10);
    }

    @Test
    void testReset() {
        // Arrange
        TransactionalTestConfig.Foo foo = fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));
        foo.setField("up");
        fooRepository.save(foo);
        fooRepository.findAll();
        fooRepository.deleteAll();
        fooRepository.rand();
        // Act
        AssertSqlQueryCount.reset();
        // Asserts
        AssertSqlQueryCount.assertCount(QueryType.CALL, 0);
        AssertSqlQueryCount.assertCount(QueryType.DELETE, 0);
        AssertSqlQueryCount.assertCount(QueryType.UPDATE, 0);
        AssertSqlQueryCount.assertCount(QueryType.SELECT, 0);
        AssertSqlQueryCount.assertCount(QueryType.INSERT, 0);
    }

    @Test
    void testThrows() {
        // Arrange
        // Act
        Assertions.assertThrows(Exception.class,
                                () -> AssertSqlQueryCount.assertCount(QueryType.SELECT, 1));
        // Asserts
    }
}