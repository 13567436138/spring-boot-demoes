package com.mark.demo.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/*
*hxp(hxpwangyi@126.com)
*2017年10月11日
*
*/
public class TestJob extends QuartzJobBean {


	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.println("test");
	}

}
