package com.mark.demo.security.aop;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*
*hxp(hxpwangyi@126.com)
*2017年10月19日
*
*/
@Aspect
@Component
public class DaoAdvice {
	
	@Pointcut("@annotation(com.mark.demo.security.anno.pointCutAnno)")
	public void pointCut(){
		
	}
	@Before("pointCut()")
	public void before(){
		System.out.println("------------------before--------------------");
	}
	
	@After("execution(* com.mark.demo.security.controller.*.*(..))")
	public void after(){
		System.out.println("------------------after-----------------------");
	}
	
	@AfterReturning(pointcut="execution(* com.mark.demo.security.service.impl.MenuServiceImpl.*(..))",returning="result")
	public void afterReturn(JoinPoint jp,Object result){
		System.out.println(((List)result).size());
	}
}
