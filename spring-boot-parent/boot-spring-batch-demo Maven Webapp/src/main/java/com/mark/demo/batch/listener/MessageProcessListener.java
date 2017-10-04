package com.mark.demo.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/*
*hxp(hxpwangyi@126.com)
*2017年10月4日
*
*/
public class MessageProcessListener implements JobExecutionListener {
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("******************start***********************");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("******************end***********************");
	}

}
