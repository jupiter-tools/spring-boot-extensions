package com.jupiter.tools.spring.test.web.extension.ribbon;

import com.jupiter.tools.spring.test.web.annotation.EnableEmbeddedWebServerTest;
import feign.Client;
import feign.Feign;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 06.02.2019.
 *
 * @author Korovin Anatoliy
 */
@EnableEmbeddedWebServerTest
@RedirectRibbonToEmbeddedWebServer("test-service")
class RedirectRibbonExtensionByFeignTest {

    @Autowired
    private CustomFeign customFeign;

    @Test
    void feign() {
        String test = customFeign.test();
        assertThat(test).isEqualTo("123");
    }

    @TestConfiguration
    public static class TestCfg {

        @RestController
        @RequestMapping("/messages")
        public class TestApi {

            @GetMapping("/test")
            public String test() {
                return "123";
            }
        }

        @Bean
        public CustomFeign customFeign(Client client) {
            return Feign.builder()
                        .contract(new SpringMvcContract())
                        .client(client)
                        .target(CustomFeign.class, "http://test-service");
        }
    }

    public interface CustomFeign {
        @GetMapping("/messages/test")
        String test();
    }

}