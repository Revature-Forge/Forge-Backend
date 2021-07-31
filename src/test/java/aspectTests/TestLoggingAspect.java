package aspectTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.forge.revature.aspects.LoggingAspect;
import com.forge.revature.controllers.UserController;
import com.forge.revature.repo.UserRepo;

/**
 * 
 * Basic testing for Logging Aspect. Tests for no side effects, and exceptions
 * are thrown
 * 
 * @author Aron Jang
 *
 */

class TestLoggingAspect {
	private LoggingAspect LA = new LoggingAspect();
	private UserController controllerProxy;
	JoinPoint JP;
	Exception e;
	ProceedingJoinPoint PJP;

	@MockBean
	UserRepo repo;

	@BeforeEach
	void init() {
		AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(new UserController(repo));
		aspectJProxyFactory.addAspect(LA);
		DefaultAopProxyFactory proxyFactory = new DefaultAopProxyFactory();
		AopProxy aopProxy = proxyFactory.createAopProxy(aspectJProxyFactory);

		controllerProxy = (UserController) aopProxy.getProxy();
	}

	@Test
	void testBeanPointCutNoSideEffects() {
		LoggingAspect LAOld = LA;
		LA.beanPointcut();
		assertEquals(LAOld, LA);
	}

	@Test
	void testPackagePointCutNoSideEffects() {
		LoggingAspect LAOld = LA;
		LA.packagePointcut();
		assertEquals(LAOld, LA);
	}

	@Test
	void testExpectedException() {
		Assertions.assertThrows(Exception.class, () -> {
			controllerProxy.getByID(-1);
		});
	}

	@Test
	void logAfterThrowing() {
		LA = mock(LoggingAspect.class);
		JP = mock(JoinPoint.class);
		LA.logAfterThrowing(JP, e);
		verify(LA, times(1)).logAfterThrowing(JP, e);
	}

	@Test
	void logAfterThrowingNull() {
		LA = mock(LoggingAspect.class);
		JP = mock(JoinPoint.class);
		LA.logAfterThrowing(JP, e);
		verify(LA, times(1)).logAfterThrowing(JP, e);
	}

	@Test
	void logAroundTest() throws Throwable {
		LA = mock(LoggingAspect.class);
		PJP = mock(ProceedingJoinPoint.class);
		LA.logAround(PJP);
		verify(PJP, never()).proceed();
		verify(LA, times(1)).logAround(PJP);
	}
	
}
