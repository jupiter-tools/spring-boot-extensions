package com.jupiter.tools.spring.test.postgres.extension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 23.01.2019.
 *
 * TODO: replace on javadoc
 *
 * @author Korovin Anatoliy
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
@ExtendWith(PostgresTcExtension.class)
@ExtendWith(TraceSqlExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostgresTcExtensionTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Sql("/stored_functions/test_func.sql")
    void testStoredFunc() {
        // Arrange
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("rnd");
        // Act
        query.execute();
        // Assert
        List resultList = query.getResultList();
        int rnd = (int) resultList.get(0);
        Assertions.assertThat(rnd).isEqualTo(123);
    }
}