package com.jupiter.tools.spring.test.postgres.extension;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created on 13.06.2022.
 *
 * @author Sergey Nikolaev
 */
@DataJpaTest
@ExtendWith(PostgresTcExtensionCustomVersionTest.InitializePropertiesExtension.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(PostgresTcExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostgresTcExtensionCustomVersionTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testCustomPostgresVersion() {
        // Arrange
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("version");
        // Act
        query.execute();
        // Assert
        Object result = query.getSingleResult();
        Assertions.assertThat(result.toString()).contains("PostgreSQL 12");
    }

    public static class InitializePropertiesExtension<writer> implements Extension, AfterAllCallback {
        public static final String SOURCE;
        static {
            SOURCE = PostgresTcExtensionCustomVersionTest.class.getResource("/").getPath() + "tc-extension.properties";
            try(FileWriter writer = new FileWriter(SOURCE)) {
                writer.write("tc-extension.postgres.docker-image-version=12");
            } catch (IOException e) {
                e.printStackTrace();
                Assertions.fail("Fail create tc-extension.properties");
            }
        }

        @Override
        public void afterAll(ExtensionContext extensionContext) throws Exception {
            try {
                new File(SOURCE).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
