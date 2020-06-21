package com.jupiter.tools.spring.test.core.expected.list.messages;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupitertools.datasetroll.DataSet;

/**
 * DataSet to represent a list of messages.
 * Matches canonical class name of a message object to the list of messages converted to Map.
 *
 * @author Korovin Anatoliy
 */
public class MessagesDataSet implements DataSet {

	private final List<Object> messages;
	private final ObjectMapper objectMapper;

	/**
	 * Make the DataSet from a list of received messages.
	 *
	 * @param messages the list of messages objects
	 */
	public MessagesDataSet(List<Object> messages) {
		this.messages = messages;
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Map<String, List<Map<String, Object>>> read() {

		Map<String, List<Map<String, Object>>> result = new HashMap<>();

		for (Object message : messages) {

			String className = message.getClass().getCanonicalName();
			Map<String, Object> messageFields = objectMapper.convertValue(message, Map.class);

			List<Map<String, Object>> entry = result.get(className);
			if (entry == null) {
				result.put(className, new ArrayList<>(Arrays.asList(messageFields)));
			} else {
				entry.add(messageFields);
			}
		}

		return result;
	}
}
