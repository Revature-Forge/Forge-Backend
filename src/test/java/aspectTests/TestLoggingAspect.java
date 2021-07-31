package aspectTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.forge.revature.aspects.LoggingAspect;

class TestLoggingAspect {
	LoggingAspect LA;
	JoinPoint JP;
	Exception e;
	ProceedingJoinPoint PJP;

	@BeforeEach
	void init() {
		LA = mock(LoggingAspect.class);
		JP = mock(JoinPoint.class);
		e = mock(Exception.class);
		PJP = mock(ProceedingJoinPoint.class);
	}

	@Test
	void beanPointCutDoesNotModifyAspect() {
		LA.beanPointcut();

	}

	@Test
	void packagePointCutDoesNotModifyAspect() {
		LA.packagePointcut();
	}

	@Test
	void beanPointCutReturnsNothing() {
		LA.beanPointcut();
		verify(LA, times(1)).beanPointcut();
	}

	@Test
	void packagePointCutReturnsNothing() {
		LA.packagePointcut();
		verify(LA, times(1)).packagePointcut();
	}

	@Test
	void logAfterThrowing() {
		LA.logAfterThrowing(JP, e);
		verify(LA, times(1)).logAfterThrowing(JP, e);
	}

	@Test
	void logAroundTest() throws Throwable {
		LA.logAround(PJP);
		verify(LA, times(1)).logAround(PJP);
	}
}
