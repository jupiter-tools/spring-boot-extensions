package com.jupiter.tools.spring.test.core.expected.list.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupitertools.datasetroll.DataSet;
import com.jupitertools.datasetroll.expect.MatchDataSets;
import com.jupitertools.datasetroll.importdata.ImportFile;
import com.jupitertools.datasetroll.importdata.JsonImport;

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

    public AssertReceivedMessages(ExpectedMessagesOptions expectedMessagesOptions,
                                  MessageBroker messageBroker) {
        this.expectedMessagesOptions = expectedMessagesOptions;
        this.messageBroker = messageBroker;
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
	    if (expectedMessagesOptions.getExpectedDataSetPreProcessor() != null) {
		    expectedDataSet = expectedMessagesOptions.getExpectedDataSetPreProcessor().run(expectedDataSet);
	    }

        if (isEmptyDataSet(expectedDataSet)) {
            processingEmptyDataSet(expectedMessagesOptions);
            return;
        }

        processingDataSet(expectedMessagesOptions, expectedDataSet);
    }

	/**
	 * Waits for messages and throws an exception if receive something during the waiting timeout interval.
	 */
	public void doAssertSilence() {

		for (String queue : expectedMessagesOptions.getAllQueues()) {
			Object message = messageBroker.receive(queue, expectedMessagesOptions.getTimeout());

			if (message != null) {
				new Fail("not expected but found:").withObject(message).fire();
			}
		}
	}


    private boolean isEmptyDataSet(DataSet dataSet) {
        Map<String, List<Map<String, Object>>> readDataSet = dataSet.read();
        return readDataSet.isEmpty() || readDataSet.entrySet()
                                                   .stream()
                                                   .allMatch(e -> e.getValue().isEmpty());
    }

    private void processingEmptyDataSet(ExpectedMessagesOptions expectedMessagesOptions) {

        Object message = messageBroker.receive(expectedMessagesOptions.getQueue(),
                                               expectedMessagesOptions.getTimeout());

        if (message != null) {
            new Fail("not expected but found:").withObject(message).fire();
        }
    }

    private void processingDataSet(ExpectedMessagesOptions expectedMessagesOptions,
                                   DataSet expectedDataSet){

        List<Object> receivedMessages = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        while (true) {

            Object message = messageBroker.receive(expectedMessagesOptions.getQueue(),
                                                   expectedMessagesOptions.getTimeout());

            if (message == null) {
                new Fail("expected but not found:").withObject(expectedDataSet.read()).fire();
            }

            receivedMessages.add(message);
            DataSet actualDataSet = new MessagesDataSet(receivedMessages);

            if(isWaitingMoreMessages(startTime, actualDataSet, expectedDataSet)){
                continue;
            }

	        if (expectedMessagesOptions.getActualDataSetPreProcessor() != null) {
		        actualDataSet = expectedMessagesOptions.getActualDataSetPreProcessor()
		                                               .run(actualDataSet);
	        }

	        new MatchDataSets(actualDataSet, expectedDataSet).check();
	        return;
        }
    }

    private boolean isWaitingMoreMessages(long startTime, DataSet actual, DataSet expected) {

        if (timeLimit(startTime, expectedMessagesOptions.getTimeout())) {
            System.out.println("Timeout was reached.");
            return false;
        }

        return getMessageCount(actual) < getMessageCount(expected);
    }

    private int getMessageCount(DataSet dataSet) {
        return dataSet.read()
                      .entrySet()
                      .stream()
                      .mapToInt(e -> e.getValue().size())
                      .sum();
    }

    private boolean timeLimit(long startTime, long timeout) {
        return (System.currentTimeMillis() - startTime > timeout);
    }
}
