package converterTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.forge.revature.converter.HashMapConverter;

/**
 * 
 * Tests for converting Map to JsonString and back
 * 
 * @author Aron Jang
 *
 */
public class converterTest {

	HashMapConverter HMC = new HashMapConverter();

	@Test
	void testConverttoString() {
		Map<String, String> flag = new HashMap<>();
		flag.put("Key", "Value");
		String response = HMC.convertToDatabaseColumn(flag);
		String expectedResponse = "{\"Key\":\"Value\"}";
		assertEquals(response, expectedResponse);
	}

	@Test
	void testConverttoMap() {
		String JSON = "{\"Key\":\"Value\"}";
		Map<String, String> flag = HMC.convertToEntityAttribute(JSON);
		Map<String, String> expectedFlag = new HashMap<>();
		expectedFlag.put("Key", "Value");
		assertEquals(flag, expectedFlag);
	}

}
