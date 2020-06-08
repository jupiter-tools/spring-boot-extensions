package com.jupiter.tools.spring.test.postgres.annotation.meta.v2;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.TransactionalTestConfig;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;


/**
 * Created on 23.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnablePostgresIntegrationTest
@Import(TransactionalTestConfig.class)
class EnablePostgresIntegrationTestTest {

    @Autowired
    private TransactionalTestConfig.TestService testService;

    @Test
    @DataSet(value = "/datasets/empty.json", cleanBefore = true, cleanAfter = true, executorId = "rnd-name")
    @ExpectedDataSet(value = "/datasets/expected.json", ignoreCols = "ID")
    void testCreate() {
        testService.ok();
    }
}