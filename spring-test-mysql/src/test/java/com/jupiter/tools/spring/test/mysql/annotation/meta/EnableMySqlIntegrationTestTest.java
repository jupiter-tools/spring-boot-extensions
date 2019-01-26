package com.jupiter.tools.spring.test.mysql.annotation.meta;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.mysql.TransactionalTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

/**
 * Created on 26.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableMySqlIntegrationTest
@Import(TransactionalTestConfig.class)
class EnableMySqlIntegrationTestTest {

    @Autowired
    private TransactionalTestConfig.TestService testService;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/expected.json", ignoreCols = "ID")
    void testCreate() {
        testService.ok();
    }
}
