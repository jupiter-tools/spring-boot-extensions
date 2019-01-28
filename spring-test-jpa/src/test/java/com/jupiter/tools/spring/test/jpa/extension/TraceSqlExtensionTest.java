package com.jupiter.tools.spring.test.jpa.extension;

import com.jupiter.tools.spring.test.jpa.tracesql.AssertSqlCount;
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
        AssertSqlCount.reset();
    }

    @Test
    void testInsert() {
        // Act
        fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));
        // Assert
        AssertSqlCount.assertInsertCount(1);
    }

    @Test
    void testSelect() {
        // Act
        fooRepository.findAll();
        // Asserts
        AssertSqlCount.assertSelectCount(1);
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
        AssertSqlCount.assertUpdateCount(1);
    }

    @Test
    void testDelete() {
        // Arrange
        fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));
        // Act
        fooRepository.deleteAll();
        // Asserts
        AssertSqlCount.assertDeleteCount(1);
    }

    @Test
    void testCall() {
        // Act
        fooRepository.rand();
        // Asserts
        AssertSqlCount.assertCallCount(1);
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
        AssertSqlCount.assertCallCount(10);
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
        AssertSqlCount.reset();
        // Asserts
        AssertSqlCount.assertCallCount(0);
        AssertSqlCount.assertDeleteCount(0);
        AssertSqlCount.assertUpdateCount(0);
        AssertSqlCount.assertSelectCount(0);
        AssertSqlCount.assertInsertCount(0);
    }

    @Test
    void testThrows() {
        // Arrange
        // Act
        Assertions.assertThrows(Exception.class,
                                () -> AssertSqlCount.assertSelectCount(1));
        // Asserts
    }
}