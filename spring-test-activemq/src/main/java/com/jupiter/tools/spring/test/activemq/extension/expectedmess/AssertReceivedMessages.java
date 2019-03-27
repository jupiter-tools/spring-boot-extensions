package com.jupiter.tools.spring.test.activemq.extension.expectedmess;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.tools.spring.test.activemq.annotation.ExpectedMessages;
import com.jupiter.tools.spring.test.core.importdata.DataSet;
import com.jupiter.tools.spring.test.core.importdata.ImportFile;
import com.jupiter.tools.spring.test.core.importdata.JsonImport;

/**
 * Created on 27.03.2019.
 *
 * TODO: replace on javadoc
 *
 * @author Korovin Anatoliy
 */
public class AssertReceivedMessages {

    private final ExpectedMessages expectedMessages;
    private final MessageBroker messageBroker;
    private final ObjectMapper mapper;

    public AssertReceivedMessages(ExpectedMessages expectedMessages,
                                  MessageBroker messageBroker) {
        this.expectedMessages = expectedMessages;
        this.messageBroker = messageBroker;
        this.mapper = new ObjectMapper();
    }

    /**
     * Try to receive messages from broker
     * and match them with expected data set.
     */
    public void doAssert() {

        if (expectedMessages == null) {
            return;
        }

        DataSet expectedDataSet = new JsonImport(new ImportFile(expectedMessages.messagesFile()));

        if (isEmptyDataSet(expectedDataSet)) {
            processingEmptyDataSet(expectedMessages);
            return;
        }

        processingDataSet(expectedMessages, expectedDataSet);
    }

    private boolean isEmptyDataSet(DataSet dataSet) {
        Map<String, List<Map<String, Object>>> readDataSet = dataSet.read();
        return readDataSet.isEmpty() || readDataSet.entrySet()
                                                   .stream()
                                                   .allMatch(e -> e.getValue().isEmpty());
    }

    private void processingEmptyDataSet(ExpectedMessages expectedMessages) {

        if (expectedMessages.ignoreUnexpected()) {
            return;
        }

        Object message = messageBroker.receive(expectedMessages.queue(),
                                               expectedMessages.timeout());

        if (message != null) {
            new Fail("not expected but found:").withObject(message).fire();
        }
    }

    private void processingDataSet(ExpectedMessages expectedMessages,
                                   DataSet expectedDataSet) {

        Map<String, List<Map<String, Object>>> expectedDataMap = expectedDataSet.read();
        long startTime = System.currentTimeMillis();
        boolean processing = true;
        while (processing) {

            Object message = messageBroker.receive(expectedMessages.queue(), expectedMessages.timeout());

            if (message == null) {
                new Fail("expected but not found:").withObject(expectedDataMap).fire();
            }

            assertReceivedMessage(expectedMessages, expectedDataMap, message);

            if (isEmptyDataMap(expectedDataMap) || timeLimit(startTime, expectedMessages.timeout())) {
                processing = false;
            }
        }
    }

    private void assertReceivedMessage(ExpectedMessages expectedMessages,
                                       Map<String, List<Map<String, Object>>> expectedDataMap,
                                       Object message) {

        String className = message.getClass().getCanonicalName();

        if (!expectedDataMap.containsKey(className)) {
            if (expectedMessages.ignoreUnexpected()) {
                return;
            }
            new Fail("not expected but found:").withObject(message).fire();
        }

        Map<String, Object> map = mapper.convertValue(message, Map.class);
        if (!expectedDataMap.get(className).contains(map)) {
            if (expectedMessages.ignoreUnexpected()) {
                return;
            }
            new Fail("not expected but found: `" + className + "`:").withObject(message).fire();
        }

        removeEntryFromExpected(expectedDataMap, className, map);
    }

    private boolean timeLimit(long startTime, int timeout) {
        return (System.currentTimeMillis() - startTime > timeout);
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

    private boolean isEmptyDataMap(Map<String, List<Map<String, Object>>> dataMap) {
        return dataMap.values()
                      .stream()
                      .allMatch(List::isEmpty);
    }
}
