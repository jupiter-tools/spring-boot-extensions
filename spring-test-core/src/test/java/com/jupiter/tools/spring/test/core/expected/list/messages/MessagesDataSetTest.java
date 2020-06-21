package com.jupiter.tools.spring.test.core.expected.list.messages;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessagesDataSetTest {

	@Test
	void singleTypeOfMessages() {
		Foo foo1 = new Foo("111");
		Foo foo2 = new Foo("222");
		List<Object> messages = Arrays.asList(foo1, foo2);
		MessagesDataSet dataSet = new MessagesDataSet(messages);
		// Act
		Map<String, List<Map<String, Object>>> map = dataSet.read();
		// Assert
		assertThat(map).containsKeys(Foo.class.getCanonicalName());
		assertThat(map.get(Foo.class.getCanonicalName())).contains(ImmutableMap.of("name", "111"),
		                                                           ImmutableMap.of("name", "222"));
	}

	@Test
	void multipleTypes() {
		Foo foo1 = new Foo("111");
		Foo foo2 = new Foo("222");
		Bar bar = new Bar("AAA");
		List<Object> messages = Arrays.asList(foo1, foo2, bar);
		MessagesDataSet dataSet = new MessagesDataSet(messages);
		// Act
		Map<String, List<Map<String, Object>>> map = dataSet.read();
		// Assert
		assertThat(map).containsKeys(Foo.class.getCanonicalName());
		assertThat(map.get(Foo.class.getCanonicalName())).contains(ImmutableMap.of("name", "111"),
		                                                           ImmutableMap.of("name", "222"));
		assertThat(map).containsKeys(Bar.class.getCanonicalName());
		assertThat(map.get(Bar.class.getCanonicalName())).contains(ImmutableMap.of("value", "AAA"));
	}

	@Data
	@AllArgsConstructor
	static class Foo {
		private String name;
	}

	@Data
	@AllArgsConstructor
	static class Bar {
		private String value;
	}
}