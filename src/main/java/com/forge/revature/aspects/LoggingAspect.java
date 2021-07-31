package com.forge.revature.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * This class is an Aspect for logging execution of methods in the Repository
 * and Controller Components. Logs are currently at the info level, however, for
 * production, it is recommended to change logging methods at INFO to DEBUG.
 * 
 * beanPointcut() - Pointcut that matches all components
 * packagePointcut() - Pointcut that matches all beans in listed packages
 * logAfterThrowing() - Advice that logs after a method throws an exception
 * logAround() - Advice that logs when entering and exiting method 
 * 
 * @author Aron Jang
 *
 */
@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Pointcut that matches all Repository Components and Rest Controllers
	 */
	@Pointcut("within(@org.springframework.stereotype.Repository *)"
			+ " || within(@org.springframework.web.bind.annotation.RestController *)")
	public void beanPointcut() {
		// PointCuts are empty, implementation is in the advice below
	}

	/**
	 * Pointcut that matches all Spring beans in given packages
	 */
	@Pointcut("within(com.forge.revature.controllers..*)" + " || within(com.forge.revature.repo..*)")
	public void packagePointcut() {
		// PointCuts are empty, implementation is in the advice below
	}

	/**
	 * Advice that logs methods when they thrown an exception
	 *
	 * @param joinPoint join point for advice
	 * @param e         exception
	 */
	@AfterThrowing(pointcut = "packagePointcut() && beanPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.error("Exception thrown in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), e.getMessage());
	}

	/**
	 * Advice that logs when around a method (when the method is entered and exited)
	 * For now, no other information is displayed
	 *
	 * @param joinPoint join point for advice
	 * @return result
	 * @throws Throwable throws IllegalArgumentException
	 */
	@Around("packagePointcut() && beanPointcut()")
	public Object logAround(ProceedingJoinPoint pJoinPoint) throws Throwable {

		log.info("Enter: {}.{}()", pJoinPoint.getSignature().getDeclaringTypeName(),
				pJoinPoint.getSignature().getName());

		try {
			Object result = pJoinPoint.proceed();

			log.info("Exit: {}.{}()", pJoinPoint.getSignature().getDeclaringTypeName(),
					pJoinPoint.getSignature().getName());

			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal Argument Exception Thrown: {} in {}.{}()", Arrays.toString(pJoinPoint.getArgs()),
					pJoinPoint.getSignature().getDeclaringTypeName(), pJoinPoint.getSignature().getName());
			throw e;
		}
	}

}
