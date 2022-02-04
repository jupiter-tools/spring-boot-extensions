package com.jupiter.tools.spring.test.mssql.annotation.meta;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.mssql.TransactionalTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

/**
 * Created on 26.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableMsSqlIntegrationTest
@Import(TransactionalTestConfig.class)
class EnableMsSqlIntegrationTestTest {

    @Autowired
    private TransactionalTestConfig.TestService testService;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/expected.json", ignoreCols = "ID")
    void testCreate() {
        testService.ok();
    }
}
