package com.jupiter.tools.spring.test.activemq.extension.expected;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created on 08.02.2019.
 *
 * TODO: replace on javadoc
 *
 * @author Korovin Anatoliy
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bar {
    private String name;
    private int count;
}
