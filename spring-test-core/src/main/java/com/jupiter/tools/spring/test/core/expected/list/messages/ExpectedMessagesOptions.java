package com.jupiter.tools.spring.test.core.expected.list.messages;

import lombok.Builder;
import lombok.Data;

/**
 * Created on 27.03.2019.
 *
 * Options to work with {@link AssertReceivedMessages}.
 *
 * @author Korovin Anatoliy
 */
@Data
@Builder
public class ExpectedMessagesOptions {

    /**
     * The name of a queue(or topic) for messages
     */
    private String queue;

    /**
     * The timeout for wait messages after the test execution
     */
    private long timeout;

    /**
     * The path to the JSON file with expected messages
     */
    private String messagesFile;

    /**
     * Applies to the expected data-set after retrieve from json-file
     */
    private DataSetPreProcessor expectedDataSetPreProcessor;

    /**
     * Applies to the actual data-set after receiving messages from queue(topic)
     */
    private DataSetPreProcessor actualDataSetPreProcessor;

    /**
     * The list of queues(or topics) to wait for messages when using {@link AssertReceivedMessages#doAssertSilence()}
     */
    private String[] allQueues;
}
