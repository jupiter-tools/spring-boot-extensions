package com.jupiter.tools.spring.test.rabbitmq.extension.expected.list.messages;

import com.jupiter.tools.spring.test.core.expected.list.messages.ExpectedMessagesOptions;
import com.jupiter.tools.spring.test.rabbitmq.annotation.ExpectedMessages;

/**
 * Created on 28.03.2019.
 *
 * Convert from {@link ExpectedMessages} to {@link ExpectedMessagesOptions}
 *
 * @author Korovin Anatoliy
 */
public class ExpectedMessagesMapper {

    private final ExpectedMessages expectedMessages;

    public ExpectedMessagesMapper(ExpectedMessages expectedMessages){
        this.expectedMessages = expectedMessages;
    }

    public ExpectedMessagesOptions getOptions() {
        return ExpectedMessagesOptions.builder()
                                      .messagesFile(expectedMessages.messagesFile())
                                      .queue(expectedMessages.queue())
                                      .timeout(expectedMessages.timeout())
                                      .build();
    }
}
