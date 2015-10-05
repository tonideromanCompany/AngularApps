package com.indra.api.security;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * @author arommartinez
 *
 */

@Component
@Aspect
public class AuthMethod {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthMethod.class);
	
	@Around(value="@annotation(com.indra.api.security.Auth)")
	public Object doAccess(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token = request.getHeader("Authorization");
		if(token == null){
			LOGGER.info("Null request token");
			return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED);
		}
		else{
			if(UserCache.getInstance().get(token.substring(1,token.length()-1))==null){
				LOGGER.warn("Request Token is not in cache");
				return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED);
			}
			else
			return pjp.proceed();
		}
    }
}
