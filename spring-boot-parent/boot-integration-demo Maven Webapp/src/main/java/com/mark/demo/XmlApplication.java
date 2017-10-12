package com.mark.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.samples.async.gateway.MathServiceGateway;

/*
*hxp(hxpwangyi@126.com)
*2017年10月12日
*
*/
public class XmlApplication {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:math-service-config.xml");
		MathServiceGateway gateWay=(MathServiceGateway)context.getBean("mathService");
		int result=gateWay.multiplyByTwo(200).get();
		System.out.println(result);
	}

}
