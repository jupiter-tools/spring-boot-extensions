package com.jupiter.tools.spring.test.mssql.annotation.meta;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.mssql.TransactionalTestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created on 26.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableMsSqlDataTest
@Import(TransactionalTestConfig.class)
class EnableMsSqlDataTestTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionalTestConfig.FooRepository repository;

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

    @Test
    @Commit
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/expected.json", ignoreCols = "ID")
    void testCreate() {

        repository.saveAndFlush(TransactionalTestConfig.Foo.builder()
                                                           .field("tru la la..")
                                                           .build());
    }
}
