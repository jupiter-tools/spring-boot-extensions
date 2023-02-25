package com.jupiter.tools.spring.test.web.extension.ribbon;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.support.ServiceInstanceListSuppliers;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory.NAMESPACE;
import static org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory.PROPERTY_NAME;

/**
 * @author Gennady Kuzmin
 */
public class LocalHostLoadBalancerClientFactory extends NamedContextFactory<LoadBalancerClientSpecification>
        implements ReactiveLoadBalancer.Factory<ServiceInstance> {

    private final Map<String, ServiceInstance> instances = new HashMap<>();
    private final Environment environment;

    public LocalHostLoadBalancerClientFactory(Environment environment) {
        super(LoadBalancerClientConfiguration.class, NAMESPACE, PROPERTY_NAME);

        this.environment = environment;
    }

    @Override
    public LoadBalancerProperties getProperties(String serviceId) {
        return new LoadBalancerProperties();
    }

    @Override
    public ReactiveLoadBalancer<ServiceInstance> getInstance(String serviceId) {
        ServiceInstance serviceInstance = instances.isEmpty() ?
                                          toServiceInstances(serviceId) :
                                          instances.get(serviceId);

        return new RoundRobinLoadBalancer(ServiceInstanceListSuppliers.toProvider(serviceId, serviceInstance), serviceId);
    }

    private ServiceInstance toServiceInstances(String serviceId) {
        int port = environment.getProperty("local.server.port", Integer.class);
        return new DefaultServiceInstance(serviceId, serviceId, "127.0.0.1", port, false);
    }

    public void clearInstances() {
        instances.clear();
    }

    public void addInstances(String... serviceIds) {
        for (String serviceId : serviceIds) {
            instances.put(serviceId, toServiceInstances(serviceId));
        }
    }
}
