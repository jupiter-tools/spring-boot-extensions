package com.jupiter.tools.spring.test.mysql.annotation;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.jupiter.tools.spring.test.mysql.TransactionalTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 26.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableMySqlTestContainers
//---->
@SpringBootTest
@DBRider
@ExtendWith(SpringExtension.class)
@Import(TransactionalTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EnableMySqlTestContainersTest {

    @Autowired
    private TransactionalTestConfig.TestService testService;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/expected.json", ignoreCols = "ID")
    void testCreate() {
        testService.ok();
    }
}
