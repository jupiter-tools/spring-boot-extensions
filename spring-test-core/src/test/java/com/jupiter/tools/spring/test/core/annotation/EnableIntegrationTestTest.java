package com.jupiter.tools.spring.test.core.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 29.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableIntegrationTest
class EnableIntegrationTestTest {

    @Autowired
    private String testBean;

    @Test
    void testDi() {
        assertThat(testBean).isNotNull()
                            .isEqualTo("test");
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public String testBean() {
            return "test";
        }
    }
}
