package com.jupiter.tools.spring.test.activemq.extension;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.tools.spring.test.activemq.annotation.ExpectedMessages;
import com.jupiter.tools.spring.test.core.importdata.ImportFile;
import com.jupiter.tools.spring.test.core.importdata.JsonImport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created on 07.02.2019.
 *
 * This extension provides an ability to assert sent messages after test execution.
 * You can declare a list of expected messages in JSON format.
 *
 * @author Korovin Anatoliy
 */
public class ExpectedMessagesExtension implements BeforeAllCallback, AfterEachCallback {

    private JmsTemplate jmsTemplate;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        ExpectedMessages expectedMessages = context.getRequiredTestMethod()
                                                   .getAnnotation(ExpectedMessages.class);

        if (expectedMessages == null) {
            return;
        }

        Map<String, List<Map<String, Object>>> expected = new JsonImport(new ImportFile(expectedMessages.messagesFile())).read();

        jmsTemplate.setReceiveTimeout(expectedMessages.timeout());
        ObjectMapper mapper = new ObjectMapper();

        long startTime = System.currentTimeMillis();
        boolean processing = true;
        while (processing) {
            Object obj = jmsTemplate.receiveAndConvert(expectedMessages.queue());
            if (obj == null) {
                Assertions.fail("expected but not found: \n" + mapper.writeValueAsString(expected));
            }

            String className = obj.getClass().getCanonicalName();

            if (!expected.containsKey(className)) {
                if (expectedMessages.ignoreUnexpected()) {
                    continue;
                }
                Assertions.fail("not expected but found: \n" + mapper.writeValueAsString(obj));
            }

            Map<String, Object> map = mapper.convertValue(obj, Map.class);
            if (!expected.get(className).contains(map)) {
                if (expectedMessages.ignoreUnexpected()) {
                    continue;
                }
                Assertions.fail("not expected but found: \n" +
                                "`" + className + "` :\n" +
                                mapper.writeValueAsString(obj));
            }

            expected.get(className).remove(map);
            if (dataSetIsEmpty(expected) || timeLimit(startTime, expectedMessages.timeout())) {
                processing = false;
            }
        }
    }

    private boolean timeLimit(long startTime, int timeout) {
        return (System.currentTimeMillis() - startTime > timeout);
    }

    private boolean dataSetIsEmpty(Map<String, List<Map<String, Object>>> dataSet) {
        return dataSet.values()
                      .stream()
                      .allMatch(List::isEmpty);
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        jmsTemplate = SpringExtension.getApplicationContext(context)
                                     .getBean(JmsTemplate.class);

        if (jmsTemplate == null) {
            throw new RuntimeException("Not found the JmsTemplate bean in the current spring context.");
        }
    }
}
