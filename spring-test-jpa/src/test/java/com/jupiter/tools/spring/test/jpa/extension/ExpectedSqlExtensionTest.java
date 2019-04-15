package com.jupiter.tools.spring.test.jpa.extension;

import java.util.HashSet;
import java.util.Set;

import com.jupiter.tools.spring.test.jpa.annotation.ExpectedSqlQuery;
import com.jupiter.tools.spring.test.jpa.tracesql.AssertSqlCount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.jupiter.tools.spring.test.jpa.tracesql.QueryType.CALL;
import static com.jupiter.tools.spring.test.jpa.tracesql.QueryType.DELETE;
import static com.jupiter.tools.spring.test.jpa.tracesql.QueryType.INSERT;
import static com.jupiter.tools.spring.test.jpa.tracesql.QueryType.SELECT;
import static com.jupiter.tools.spring.test.jpa.tracesql.QueryType.UPDATE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 26.01.2019.
 *
 * @author Korovin Anatoliy
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(TransactionalTestConfig.class)
class ExpectedSqlExtensionTest {

    @Autowired
    private TransactionalTestConfig.FooRepository fooRepository;

    @Test
    @ExpectedSqlQuery(type = INSERT, count = 1)
    void testInsert() {
        fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));
    }

    @Test
    @ExpectedSqlQuery(type = SELECT, count = 1)
    void testSelect() {
        fooRepository.findAll();
    }

    @Test
    @ExpectedSqlQuery(type = UPDATE, count = 1)
    void testUpdate() {
        // Arrange
        TransactionalTestConfig.Foo foo =
                fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));

        foo.setField("up");
        // Act
        fooRepository.save(foo);
    }

    @Test
    @ExpectedSqlQuery(type = DELETE, count = 1)
    void testDelete() {
        // Arrange
        fooRepository.save(new TransactionalTestConfig.Foo(1L, "any data"));
        // Act
        fooRepository.deleteAll();
    }

    @Test
    @ExpectedSqlQuery(type = CALL, count = 1)
    void testCall() {
        // Act
        fooRepository.rand();
    }

    @Test
    @ExpectedSqlQuery(type = CALL, count = 10)
    void testCallSeq() {
        // Arrange
        Set<Long> rands = new HashSet<>();
        // Act
        for (int i = 0; i < 10; i++) {
            rands.add(fooRepository.rand());
        }
        // Asserts
        assertThat(rands.size() > 3).isTrue();
    }
}