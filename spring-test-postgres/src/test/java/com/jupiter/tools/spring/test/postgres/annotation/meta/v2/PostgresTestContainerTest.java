package com.jupiter.tools.spring.test.postgres.annotation.meta.v2;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.jpa.annotation.EnableDataTest;
import com.jupiter.tools.spring.test.postgres.TransactionalTestConfig;
import com.jupiter.tools.spring.test.postgres.annotation.EnableSpringRider;
import com.jupiter.tools.spring.test.postgres.annotation.meta.v2.properties.DefaultSqlSettings;
import com.jupiter.tools.spring.test.postgres.customizer.PostgresTestContainer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 23.01.2019.
 *
 * @author Korovin Anatoliy
 */
@DefaultSqlSettings
@EnableDataTest
@EnableSpringRider
@PostgresTestContainer
@Import(TransactionalTestConfig.class)
class PostgresTestContainerTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionalTestConfig.FooRepository repository;

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