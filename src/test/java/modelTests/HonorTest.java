package modelTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.models.Honor;

@SpringBootTest(classes=Honor.class)
class HonorTest {

	@Test
	void customConstructorTest() {
		Honor h = new Honor("title", "description", "2021-7-28", "2021-7-28", null);
		assertSame("description", h.getDescription());
	}

}
