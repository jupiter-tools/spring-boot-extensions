package com.jupiter.tools.spring.test.core.expected.list.messages;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.tools.spring.test.core.importdata.DataSet;
import com.jupiter.tools.spring.test.core.importdata.ImportFile;
import com.jupiter.tools.spring.test.core.importdata.JsonImport;

/**
 * Created on 27.03.2019.
 *
 * Compare an expected data set with received from broker messages,
 * trying to receive messages from the broker and will wait until timeout reached.
 *
 * @author Korovin Anatoliy
 */
public class AssertReceivedMessages {

    private final ExpectedMessagesOptions expectedMessagesOptions;
    private final MessageBroker messageBroker;
    private final ObjectMapper mapper;

    public AssertReceivedMessages(ExpectedMessagesOptions expectedMessagesOptions,
                                  MessageBroker messageBroker) {
        this.expectedMessagesOptions = expectedMessagesOptions;
        this.messageBroker = messageBroker;
        this.mapper = new ObjectMapper();
    }

    /**
     * Try to receive messages from broker
     * and match them with expected data set.
     */
    public void doAssert() {

        if (expectedMessagesOptions == null) {
            return;
        }

        DataSet expectedDataSet = new JsonImport(new ImportFile(expectedMessagesOptions.getMessagesFile()));

        if (isEmptyDataSet(expectedDataSet)) {
            processingEmptyDataSet(expectedMessagesOptions);
            return;
        }

        processingDataSet(expectedMessagesOptions, expectedDataSet);
    }

    private boolean isEmptyDataSet(DataSet dataSet) {
        Map<String, List<Map<String, Object>>> readDataSet = dataSet.read();
        return readDataSet.isEmpty() || readDataSet.entrySet()
                                                   .stream()
                                                   .allMatch(e -> e.getValue().isEmpty());
    }

    private void processingEmptyDataSet(ExpectedMessagesOptions expectedMessagesOptions) {

        if (expectedMessagesOptions.isIgnoreUnexpected()) {
            return;
        }

        Object message = messageBroker.receive(expectedMessagesOptions.getQueue(),
                                               expectedMessagesOptions.getTimeout());

        if (message != null) {
            new Fail("not expected but found:").withObject(message).fire();
        }
    }

    private void processingDataSet(ExpectedMessagesOptions expectedMessagesOptions,
                                   DataSet expectedDataSet) {

        Map<String, List<Map<String, Object>>> expectedDataMap = expectedDataSet.read();
        long startTime = System.currentTimeMillis();
        boolean processing = true;
        while (processing) {

            Object message = messageBroker.receive(expectedMessagesOptions.getQueue(),
                                                   expectedMessagesOptions.getTimeout());

            if (message == null) {
                new Fail("expected but not found:").withObject(expectedDataMap).fire();
            }

            assertReceivedMessage(expectedMessagesOptions, expectedDataMap, message);

            if (isEmptyDataMap(expectedDataMap) || timeLimit(startTime, expectedMessagesOptions.getTimeout())) {
                processing = false;
            }
        }
    }

    private void assertReceivedMessage(ExpectedMessagesOptions expectedMessages,
                                       Map<String, List<Map<String, Object>>> expectedDataMap,
                                       Object message) {

        String className = message.getClass().getCanonicalName();

        if (!expectedDataMap.containsKey(className)) {
            if (expectedMessages.isIgnoreUnexpected()) {
                return;
            }
            new Fail("not expected but found:").withObject(message).fire();
        }

        Map<String, Object> map = mapper.convertValue(message, Map.class);
        if (!expectedDataMap.get(className).contains(map)) {
            if (expectedMessages.isIgnoreUnexpected()) {
                return;
            }
            new Fail("not expected but found: `" + className + "`:").withObject(message).fire();
        }

        removeEntryFromExpected(expectedDataMap, className, map);
    }

    private boolean timeLimit(long startTime, long timeout) {
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
