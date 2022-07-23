package com.jupiter.tools.spring.test.mssql.extension;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.jupiter.tools.spring.test.jpa.extension.TraceSqlExtension;
import com.jupiter.tools.spring.test.mssql.TransactionalTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 25.01.2019.
 *
 * @author Korovin Anatoliy
 */
@DBRider
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MsSqlTcExtension.class)
@ExtendWith(TraceSqlExtension.class)
@Import(TransactionalTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MsSqlTcExtensionIntegrationTest {

    @Autowired
    private TransactionalTestConfig.TestService testService;

    @Autowired
    private TransactionalTestConfig.FooRepository fooRepository;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/expected.json", ignoreCols = "ID")
    void testCreate() {
        testService.ok();
    }

    @Test
    void nativeQuery() {
        //Act & Assert
        assertThat(fooRepository.nativeQuery()).isEqualTo("3");
    }

    @Test
    void hostName() {
        // Act
        String hostName = fooRepository.nativeQuery();

        System.out.println("! Host name is " + hostName + " !");

        // Assert
        assertThat(hostName).isNotNull();
    }

}