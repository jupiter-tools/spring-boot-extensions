package com.jupiter.tools.spring.test.activemq.importdata;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convert a {@link Text} (in JSON format) to the {@link DataSet}
 *
 * @author Korovin Anatoliy
 */
public class JsonImport implements DataSet {

    private final Text text;
    private final ObjectMapper objectMapper;
    private final Logger log;

    public JsonImport(Text text) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.text = text;
        this.log = LoggerFactory.getLogger(JsonImport.class);
    }

    @Override
    public Map<String, List<Map<String, Object>>> read() {

        String content = text.read();
        try {
            return objectMapper.readValue(content,
                                          new TypeReference<Map<String, List<Map<String, Object>>>>() {
                                          });
        }
        catch (IOException e) {
            log.error("Error while parsing the next JSON file: \n{}", content, e);
            throw new RuntimeException("Error when parsing the JSON text.", e);
        }
    }
}