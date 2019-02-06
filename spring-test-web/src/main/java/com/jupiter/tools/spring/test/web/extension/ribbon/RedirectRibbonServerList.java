package com.jupiter.tools.spring.test.web.extension.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.loadbalancer.Server;

import java.util.Collections;
import java.util.List;

/**
 * Created on 06.02.2019.
 *
 * @author Korovin Anatoliy
 */
public class RedirectRibbonServerList extends AbstractServerList<Server> {

    private final List<String> clientNames;
    private IClientConfig clientConfig;


    public RedirectRibbonServerList(IClientConfig clientConfig, List<String> clientNames) {
        if (clientNames == null) {
            throw new RuntimeException("clientNames must be not null");
        }
        this.clientConfig = clientConfig;
        this.clientNames = clientNames;
    }

    @Override
    public List<Server> getInitialListOfServers() {
        return getUpdatedListOfServers();
    }

    @Override
    public List<Server> getUpdatedListOfServers() {

        // processing of default value of the RedirectRibbonToEmbeddedWebServer annotation
        if (clientNames.isEmpty()) {
            return getEmbeddedServers();
        }

        if (clientConfig != null && clientNames.contains(clientConfig.getClientName())) {
            return getEmbeddedServers();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    private List<Server> getEmbeddedServers() {
        return Collections.singletonList(new Server("127.0.0.1", getPort()));
    }

    private Integer getPort() {
        return Integer.valueOf(System.getProperty("server.port"));
    }
}
