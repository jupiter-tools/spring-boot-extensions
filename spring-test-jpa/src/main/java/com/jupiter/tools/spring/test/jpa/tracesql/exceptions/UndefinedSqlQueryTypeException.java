package com.jupiter.tools.spring.test.jpa.tracesql.exceptions;

/**
 * Created on 15.04.2019.
 * @author Korovin Anatoliy
 */
public class UndefinedSqlQueryTypeException extends RuntimeException {

    public UndefinedSqlQueryTypeException(String message) {
        super(message);
    }
}
