package com.jupiter.tools.spring.test.web.extension.ribbon;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 05.02.2019.
 *
 * @author Korovin Anatoliy
 * @author Gennady Kuzmin
 */
public class RedirectLBExtension implements Extension, BeforeEachCallback, AfterAllCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        getLoadBalancerClientFactory(context).addInstances(getClientNameFromTestClass(context));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        getLoadBalancerClientFactory(context).clearInstances();
    }

    private LocalHostLoadBalancerClientFactory getLoadBalancerClientFactory(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context).getBean(LocalHostLoadBalancerClientFactory.class);
    }

    private String[] getClientNameFromTestClass(ExtensionContext context) {
        return context.getRequiredTestClass()
                      .getAnnotation(RedirectLBToLocalHost.class)
                      .value();
    }
}
