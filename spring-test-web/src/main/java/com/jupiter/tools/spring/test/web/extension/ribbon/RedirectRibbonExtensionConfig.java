package com.jupiter.tools.spring.test.web.extension.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 06.02.2019.
 *
 * @author Korovin Anatoliy
 */
public class RedirectRibbonExtensionConfig {

    private static List<String> clientNames;

    /**
     * set list of client which will be redirect to local embedded server
     *
     * @param clients list of ribbon client names
     */
    public static void setClients(String... clients) {
        clientNames = Arrays.asList(clients);
    }

    @Bean
    public ServerList<Server> customServerList(IClientConfig ribbonClientConfig) {
        return new RedirectRibbonServerList(ribbonClientConfig, clientNames);
    }
}
