package com.jupiter.tools.spring.test.web.extension.ribbon;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * Created on 06.02.2019.
 *
 * @author Korovin Anatoliy
 * @author Gennady Kuzmin
 */
@Configuration
public class RedirectLBExtensionConfig {

    @Bean
    @Primary
    public LocalHostLoadBalancerClientFactory jupiterToolsLoadBalancerClientFactory(Environment environment) {
        return new LocalHostLoadBalancerClientFactory(environment);
    }

    @Bean
    public LoadBalancerClient blockingLoadBalancerClient(LocalHostLoadBalancerClientFactory loadBalancerClientFactory) {
        return new BlockingLoadBalancerClient(loadBalancerClientFactory);
    }
}
