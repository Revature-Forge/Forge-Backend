package aspectTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.forge.revature.aspects.LoggingAspect;

public class loggingAspectTest {

	LoggingAspect LA = new LoggingAspect();
	
	@Test
	void beanPointCutReturnsNothing() {
		LA.beanPointcut();
	}
	
	@Test
	void packagePointCutReturnsNothing() {
		LA.packagePointcut();
	}
	
	
	
}
