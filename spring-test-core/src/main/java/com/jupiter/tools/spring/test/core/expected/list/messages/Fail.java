package com.jupiter.tools.spring.test.core.expected.list.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;

/**
 * Created on 27.03.2019.
 *
 * Assert.fail with object serialization in the log.
 *
 * @author Korovin Anatoliy
 */
public class Fail {

    private final ObjectMapper mapper = new ObjectMapper();
    private String message;
    private String object;

    public Fail(String errorText) {
        message = errorText;
        object = "";
    }

    public Fail withObject(Object object) {

        if(object instanceof String){
            this.object = (String) object;
            return this;
        }

        try {
            this.object = mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Object Serialization Error");
        }
        return this;
    }

    public void fire() {
        Assertions.fail(message + "\n" + object);
    }
}
