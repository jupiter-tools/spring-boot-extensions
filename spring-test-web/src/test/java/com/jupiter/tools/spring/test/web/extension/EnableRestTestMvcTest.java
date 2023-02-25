package com.jupiter.tools.spring.test.web.extension;

import com.jupiter.tools.spring.test.web.annotation.EnableRestMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created on 29.01.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableRestMvcTest
public class EnableRestTestMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testApi() throws Exception {
        // Arrange
        // Act
        String result = mockMvc.perform(get("/api/test"))
                               // Assert
                               .andExpect(status().isOk())
                               .andReturn().getResponse().getContentAsString();

        assertThat(result).isEqualTo("hello world");
    }

    @TestConfiguration
    public static class TestConfig {

        @RestController
        @RequestMapping("api")
        public class TestController {

            @GetMapping("test")
            public String test() {
                return "hello world";
            }
        }
    }
}
