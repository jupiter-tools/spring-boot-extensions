package com.jupiter.tools.spring.test.mysql.extension;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.DataSetFormat;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.jupiter.tools.spring.test.jpa.extension.TraceSqlExtension;
import com.jupiter.tools.spring.test.mysql.TransactionalTestConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 25.01.2019.
 *
 * @author Korovin Anatoliy
 */
@DataJpaTest
@DBRider
@ExtendWith(SpringExtension.class)
@ExtendWith(MySqlTcExtension.class)
@ExtendWith(TraceSqlExtension.class)
@Import(TransactionalTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MySqlTcExtensionTest {

    @Autowired
    private TransactionalTestConfig.FooRepository repository;

    @Test
    @Commit
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/expected.json", ignoreCols = "ID")
    void testCreate() throws Exception {

        repository.saveAndFlush(TransactionalTestConfig.Foo.builder()
                                                           .field("tru la la..")
                                                           .build());
    }

    @Disabled
    @Test
    @Commit
    @DataSet(cleanBefore = true, cleanAfter = true)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @ExportDataSet(outputName = "target/dataset/export.json", format = DataSetFormat.JSON)
    void generate() throws Exception {

        repository.save(TransactionalTestConfig.Foo.builder()
                                                   .field("tru la la..")
                                                   .build());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void nativeJson() {
        assertThat(repository.nativeJson()).isNotNull();
    }
}