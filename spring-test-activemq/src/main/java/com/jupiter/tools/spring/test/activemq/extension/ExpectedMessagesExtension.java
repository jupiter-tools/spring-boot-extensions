package com.jupiter.tools.spring.test.activemq.extension;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        ExpectedMessages expectedMessages = context.getRequiredTestMethod()
                                                   .getAnnotation(ExpectedMessages.class);

        if (expectedMessages == null) {
            return;
        }

        Map<String, List<Map<String, Object>>> expected =
                new JsonImport(
                        new ImportFile(expectedMessages.messagesFile())
                ).read();

        if (isEmptyDataSet(expected)) {
            processingEmptyDataSet(expectedMessages);
            return;
        }

        processingDataSet(expectedMessages, expected);
    }

    private void processingDataSet(ExpectedMessages expectedMessages,
                                   Map<String, List<Map<String, Object>>> expectedDataSet
                                  ) throws JsonProcessingException {

        jmsTemplate.setReceiveTimeout(expectedMessages.timeout());
        long startTime = System.currentTimeMillis();
        boolean processing = true;

        while (processing) {

            Object message = jmsTemplate.receiveAndConvert(expectedMessages.queue());
            if (message == null) {
                Assertions.fail("expected but not found: \n" + mapper.writeValueAsString(expectedDataSet));
            }

            assertReceivedMessage(expectedMessages, expectedDataSet, message);

            if (dataSetIsEmpty(expectedDataSet) || timeLimit(startTime, expectedMessages.timeout())) {
                processing = false;
            }
        }
    }

    private void assertReceivedMessage(ExpectedMessages expectedMessages,
                                       Map<String, List<Map<String, Object>>> expectedDataSet,
                                       Object message) throws JsonProcessingException {

        String className = message.getClass().getCanonicalName();

        if (!expectedDataSet.containsKey(className)) {
            if (expectedMessages.ignoreUnexpected()) {
                return;
            }
            Assertions.fail("not expected but found: \n" + mapper.writeValueAsString(message));
        }

        Map<String, Object> map = mapper.convertValue(message, Map.class);
        if (!expectedDataSet.get(className).contains(map)) {
            if (expectedMessages.ignoreUnexpected()) {
                return;
            }
            Assertions.fail("not expected but found: \n" +
                            "`" + className + "` :\n" +
                            mapper.writeValueAsString(message));
        }

        removeEntryFromExpected(expectedDataSet, className, map);
    }

    private void processingEmptyDataSet(ExpectedMessages expectedMessages) throws JsonProcessingException {

        if (expectedMessages.ignoreUnexpected()) {
            return;
        }

        jmsTemplate.setReceiveTimeout(expectedMessages.timeout());

        Object obj = jmsTemplate.receiveAndConvert(expectedMessages.queue());
        if (obj != null) {
            Assertions.fail("not expected but found: \n" + mapper.writeValueAsString(obj));
        }
    }


    private boolean isEmptyDataSet(Map<String, List<Map<String, Object>>> dataset) {
        return dataset.isEmpty() || dataset.entrySet()
                                           .stream()
                                           .allMatch(e -> e.getValue().isEmpty());
    }

    /**
     * remove entry from expected map,
     * and remove key from expected if this entry was last.
     */
    private void removeEntryFromExpected(Map<String, List<Map<String, Object>>> source,
                                         String entryClassName,
                                         Map<String, Object> entry) {
        source.get(entryClassName).remove(entry);
        if (source.get(entryClassName).isEmpty()) {
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
        jmsTemplate = SpringExtension.getApplicationContext(context)
                                     .getBean(JmsTemplate.class);

        if (jmsTemplate == null) {
            throw new RuntimeException("Not found the JmsTemplate bean in the current spring context.");
        }
    }
}
