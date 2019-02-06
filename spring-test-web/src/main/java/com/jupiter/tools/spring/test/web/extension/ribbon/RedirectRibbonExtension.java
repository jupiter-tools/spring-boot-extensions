package com.jupiter.tools.spring.test.web.extension.ribbon;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Created on 05.02.2019.
 *
 * @author Korovin Anatoliy
 */
public class RedirectRibbonExtension implements Extension, BeforeEachCallback, AfterAllCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        RedirectRibbonExtensionConfig.setClients(getClientNameFromTestClass(context));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        RedirectRibbonExtensionConfig.setClients();
    }

    private String[] getClientNameFromTestClass(ExtensionContext context) {
        return context.getRequiredTestClass()
                      .getAnnotation(RedirectRibbonToEmbeddedWebServer.class)
                      .value();
    }
}
