package com.jupiter.tools.spring.test.mssql.annotation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created on 26.01.2019.
 *
 * @author Korovin Anatoliy
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
@EnableMsSqlTestContainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EnableMsSqlTestContainersTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Sql("/stored_procedures/test_procedure.sql")
    void testStoredProcedure() {
        // Arrange
        Query query = entityManager.createNativeQuery("EXEC ANSWER_TO_THE_ULTIMATE_QUESTION");

        // Act
        int result = (int) query.getSingleResult();

        //Assert
        Assertions.assertThat(result).isEqualTo(42);
    }
}
