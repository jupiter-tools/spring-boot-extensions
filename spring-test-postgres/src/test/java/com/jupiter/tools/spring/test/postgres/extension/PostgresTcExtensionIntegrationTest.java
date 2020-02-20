package com.jupiter.tools.spring.test.postgres.extension;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.jupiter.tools.spring.test.jpa.extension.TraceSqlExtension;
import com.jupiter.tools.spring.test.postgres.TransactionalTestConfig;
import com.jupiter.tools.spring.test.postgres.customizer.NewPgTc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 23.01.2019.
 *
 * @author Korovin Anatoliy
 */
@DBRider
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(TraceSqlExtension.class)
@NewPgTc
@Import(TransactionalTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostgresTcExtensionIntegrationTest {

    @Autowired
    private TransactionalTestConfig.TestService testService;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/expected.json", ignoreCols = "ID")
    void testCreate() {
        testService.ok();
    }

    @Autowired
    private TransactionalTestConfig.FooRepository fooRepository;

    @Test
    void name() {
        // Arrange
        // Act
        assertThat(fooRepository.nativeQuery()).isEqualTo("3");
        // Assert
    }

    @Test
    void pid() {
        // Arrange
        // Act
        String pid = fooRepository.getPid();
        System.out.println("! PID: "+pid+" !");
        // Assert
        assertThat(pid).isNotNull();
    }
}