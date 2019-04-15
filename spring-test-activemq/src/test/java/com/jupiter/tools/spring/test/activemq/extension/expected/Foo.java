package com.jupiter.tools.spring.test.activemq.extension.expected;

import java.io.Serializable;

/**
 * Created on 07.02.2019.
 *
 * @author Korovin Anatoliy
 */

public class Foo implements Serializable {

    private String value;

    public Foo() {
    }

    public Foo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Foo setValue(String value) {
        this.value = value;
        return this;
    }
}
