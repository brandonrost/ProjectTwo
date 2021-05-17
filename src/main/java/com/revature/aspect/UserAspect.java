package com.revature.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.revature.template.MessageTemplate;

@Aspect
@Component
public class UserAspect {
	
	
	Logger logger = LoggerFactory.getLogger(UserAspect.class); 
	
	@Pointcut("execution(public * com.revature.controller.UserController.*(..))")
	public void userControllerMethods() {}
	
	@Before("userControllerMethods()")
	public void adviceBeforeUserControllerMethods(JoinPoint jp) {
		String method = jp.getSignature().toLongString(); 
		
		logger.info("Executing @Before on method: " + method);		
	}
	
	@AfterReturning(
			pointcut = "execution(public * com.revature.controller.UserController.*(..))",
			returning = "result"
			)
	public void afterReturnUserControllerMethods(JoinPoint jp, Object result) {
		Object[] args = jp.getArgs();
		for(Object o:args) {
			logger.info(o.toString());
		}
		if (result instanceof ResponseEntity) {
			Object obj = ((ResponseEntity) result).getBody(); 
			logger.info("Executing @AfterReturning on method: " + jp.getSignature().toLongString());
			if (obj instanceof MessageTemplate) {
				logger.info("MessageTemplate warning: " + ((MessageTemplate) obj).getMessage());
			}
		}
	}

}
