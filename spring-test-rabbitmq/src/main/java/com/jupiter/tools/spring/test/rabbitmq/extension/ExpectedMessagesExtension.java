package com.jupiter.tools.spring.test.rabbitmq.extension;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.tools.spring.test.core.importdata.ImportFile;
import com.jupiter.tools.spring.test.core.importdata.JsonImport;
import com.jupiter.tools.spring.test.rabbitmq.annotation.ExpectedMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.springframework.amqp.core.AmqpTemplate;
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

    private AmqpTemplate amqpTemplate;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        ExpectedMessages expectedMessages = context.getRequiredTestMethod()
                                                   .getAnnotation(ExpectedMessages.class);

        if (expectedMessages == null) {
            return;
        }

        Map<String, List<Map<String, Object>>> expected = new JsonImport(new ImportFile(expectedMessages.messagesFile())).read();

        ObjectMapper mapper = new ObjectMapper();

        long startTime = System.currentTimeMillis();
        boolean processing = true;
        while (processing) {
            Object obj = amqpTemplate.receiveAndConvert(expectedMessages.queue(), expectedMessages.timeout());
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

            removeEntryFromExpected(expected, className, map);

            if (dataSetIsEmpty(expected) || timeLimit(startTime, expectedMessages.timeout())) {
                processing = false;
            }
        }
    }

    /**
     * remove entry from expected map,
     * and remove key from expected if this entry was last.
     */
    private void removeEntryFromExpected(Map<String, List<Map<String, Object>>> source,
                                         String entryClassName,
                                         Map<String, Object> entry){
        source.get(entryClassName).remove(entry);
        if(source.get(entryClassName).isEmpty()){
            source.remove(entryClassName);
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
        amqpTemplate = SpringExtension.getApplicationContext(context)
                                      .getBean(AmqpTemplate.class);

        if (amqpTemplate == null) {
            throw new RuntimeException("Not found the JmsTemplate bean in the current spring context.");
        }
    }
}
