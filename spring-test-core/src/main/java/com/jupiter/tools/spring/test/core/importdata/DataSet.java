package com.jupiter.tools.spring.test.core.importdata;

import java.util.List;
import java.util.Map;

/**
 * Read a map from some kind of source
 *
 * @author Korovin Anatoliy
 */
public interface DataSet {

    /**
     * Read the data set from some kind of source
     *
     * @return map with the data set,
     * map looks like this:
     * "org.package....FirstDocument" : [{"id":"1", "field":"aaa"}, {"id":"2", "field":"bbb"}],
     * "org.package....SecondDocument" : [{"id":"1", "value":"123"}, {"id":"2", "value":"456"}]
     */
    Map<String, List<Map<String, Object>>> read();
}