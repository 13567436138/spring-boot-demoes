package com.mark.demo.batch.job;

/*
*hxp(hxpwangyi@126.com)
*2017��10��3��
*
*/
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobQuartz extends QuartzJobBean{
	private static final  Logger logger = LoggerFactory.getLogger(JobQuartz.class);
	public static final String RUN_MONTH_KEY = "run.month";
	 
	@Override
	protected void executeInternal(JobExecutionContext paramJobExecutionContext){
		
		
		logger.info("beginning");	
			
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		Map<String, Object> jobDataMap = paramJobExecutionContext.getMergedJobDataMap();
		launcher.setJobRepository((JobRepository) jobDataMap.get("jobRepository"));
		launcher.setTaskExecutor(new SyncTaskExecutor());
		try {
			Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
			parameters.put(RUN_MONTH_KEY, new JobParameter("2011-2"+System.currentTimeMillis()));
			
			Long startTime=System.currentTimeMillis();
			
			JobExecution je = launcher.run((Job) jobDataMap.get("jobName"),
					new JobParameters(parameters));
			logger.info("运行时间"+(System.currentTimeMillis()-startTime));
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		logger.info("endding");	
	}

}

