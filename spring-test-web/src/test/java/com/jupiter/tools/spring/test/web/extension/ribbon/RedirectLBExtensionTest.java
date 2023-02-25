package com.jupiter.tools.spring.test.web.extension.ribbon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 06.02.2019.
 *
 * @author Korovin Anatoliy
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RedirectLBToLocalHost("test-service")
class RedirectLBExtensionTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void testRedirect() {
        String message = "test-message";
        // Act
        String size = restTemplate.getForObject("http://test-service/messages/{message}/size",
                                                String.class,
                                                message);
        // Assert
        assertThat(TestCfg.messages).containsOnly(message);
        assertThat(Integer.valueOf(size)).isEqualTo(message.length());
    }

    @Test
    void testRedirectToUnknownClient() {
        // Act
        Assertions.assertThrows(Exception.class,
                                () -> restTemplate.getForObject("http://foo-service/messages/{message}/size",
                                                                String.class,
                                                                "test-message"));
    }

    @TestConfiguration
    public static class TestCfg {

        static List<String> messages = new ArrayList<>();

        @Bean
        @LoadBalanced
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @RestController
        @RequestMapping("/messages")
        public class TestApi {

            @GetMapping("/{message}/size")
            public String getLength(@PathVariable("message") String message) {
                messages.add(message);
                return String.valueOf(message.length());
            }
        }
    }

}