package aspectTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.forge.revature.aspects.LoggingAspect;
import com.forge.revature.controllers.UserController;
import com.forge.revature.models.User;
import com.forge.revature.repo.UserRepo;

class TestLoggingAspect {
	private final LoggingAspect LA = new LoggingAspect();
	private UserController controllerProxy;
	JoinPoint JP;
	Exception e;
	ProceedingJoinPoint PJP;
	
	@BeforeEach
	void init() {
		AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(new UserController());
		aspectJProxyFactory.addAspect(LA);
		DefaultAopProxyFactory proxyFactory = new DefaultAopProxyFactory();
        AopProxy aopProxy = proxyFactory.createAopProxy(aspectJProxyFactory);
        
        controllerProxy = (UserController) aopProxy.getProxy();
	}
	
	@Test
	void whenInvokingWithNoObjectsInUserExceptionIsThrown() {
		try {
			controllerProxy.getByID(-1);
			fail("An exception should be thrown");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Cannot invoke \"com.forge.revature.repo.UserRepo.findById(Object)\" because \"this.userRepo\" is null");
		}
	}
	

	
	
	
	
	
	
}
