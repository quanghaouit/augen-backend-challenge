package com.augen.common.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IotLogger {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before("execution(* com.augen.api.rest.*.*(..))")
	public void beforeControllerMethod(JoinPoint jp) {
		putInformationLog("Start ", jp);
	}

	@After("execution(* com.augen.api.rest.*.*(..))")
	public void afterControllerMethod(JoinPoint jp) {
		putInformationLog("End ", jp);
	}

	@Before("execution(* com.augen.service.*.*(..))")
	public void beforeServiceMethod(JoinPoint jp) {
		putInformationLog("Start ", jp);
	}

	@After("execution(* com.augen.service.*.*(..))")
	public void afterServiceMethod(JoinPoint jp) {
		putInformationLog("End ", jp);
	}

	@Before("execution(* com.augen.dao.repository.*.*(..))")
	public void beforeRepositoryMethod(JoinPoint jp) {
		putInformationLog("Start ", jp);
	}

	@After("execution(* com.augen.dao.repository.*.*(..))")
	public void afterRepositoryMethod(JoinPoint jp) {
		putInformationLog("End ", jp);
	}

	private void putInformationLog(String startOrEnd, JoinPoint jp) {
		logger.info(startOrEnd + ":" + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
	}
}
