package com.forge.revature.converter;

import java.io.IOException;
import java.util.Map;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapConverter implements AttributeConverter<Map<String, String>, String> {

	@Override
	public String convertToDatabaseColumn(Map<String, String> flag) {
		ObjectMapper objectMapper = new ObjectMapper();
		String flagInfoJson = null;
		try {
			flagInfoJson = objectMapper.writeValueAsString(flag);
		} catch (final JsonProcessingException e) {
		}

		return flagInfoJson;
	}

	@Override
	public Map<String, String> convertToEntityAttribute(String flagInfoJSON) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> flagInfo = null;
		try {
			flagInfo = objectMapper.readValue(flagInfoJSON, Map.class);
		} catch (final IOException e) {
		}

		return flagInfo;
	}

}
