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
     * The name of a queue for messages
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
     * Ignore the receiving of unexpected messages (if true)
     */
    private boolean ignoreUnexpected;
}
