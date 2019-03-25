package com.jupiter.tools.spring.test.core.importdata;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created on 25.03.2019.
 *
 * TODO: replace on javadoc
 *
 * @author Korovin Anatoliy
 */
class JsonImportTest {

    @Test
    void testImport() {
        // Act
        Map<String, List<Map<String, Object>>> map = new JsonImport(this::getJson).read();
        // Asserts
        assertThat(map).isNotNull()
                       .containsKeys("com.antkorwin.springtestmongo.Bar");

        Map<String, Object> bar = map.get("com.antkorwin.springtestmongo.Bar").get(0);

        assertThat(bar.keySet()).containsOnly("id", "data");
        assertThat(bar.values()).containsOnly("55f3ed00b1375a48e61830bf", "TEST");
    }

    @Test
    void tryToImportWrongJsonFormat() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new JsonImport(() -> "{\n  123:\"abcd\"\n}").read();
        });
        assertThat(exception.getCause()).isInstanceOf(JsonParseException.class);
    }

    private String getJson() {
        return "{\n" +
               "  \"com.antkorwin.springtestmongo.Bar\": [\n" +
               "    {\n" +
               "      \"id\": \"55f3ed00b1375a48e61830bf\",\n" +
               "      \"data\": \"TEST\"\n" +
               "    }\n" +
               "  ]\n" +
               "}";
    }

}