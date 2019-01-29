package com.jupiter.tools.spring.test.web.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

import org.springframework.util.SocketUtils;

/**
 * Created on 29.08.2018.
 *
 * Bind a random available TCP port to the server.port property
 *
 * @author Korovin Anatoliy
 */
public class RandomPortBindingExtension implements Extension, BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        int port = SocketUtils.findAvailableTcpPort();
        LoggerFactory.getLogger(RandomPortBindingExtension.class).info("\n binding -> server.port = "+port+"\n");
        System.setProperty("server.port", String.valueOf(port));
    }
}
