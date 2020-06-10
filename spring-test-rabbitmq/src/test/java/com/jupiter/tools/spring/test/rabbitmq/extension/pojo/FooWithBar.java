package com.jupiter.tools.spring.test.rabbitmq.extension.pojo;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Created on 07.02.2019
 *
 * @author Korovin Anatoliy
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FooWithBar {
	Foo foo;
	Bar bar;
	FooWithBar child;
	Date time;
}
