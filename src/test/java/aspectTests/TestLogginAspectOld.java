package aspectTests;

import org.junit.jupiter.api.Test;

import com.forge.revature.aspects.LoggingAspect;

class TestLogginAspectOld {

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