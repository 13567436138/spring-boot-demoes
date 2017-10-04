package com.mark.demo.batch.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.sql.DataSource;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.core.jmx.JobDetailSupport;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.mark.demo.batch.batchMapper.MessageRowMapper;
import com.mark.demo.batch.entity.Message;
import com.mark.demo.batch.entity.ProcessedMessage;
import com.mark.demo.batch.job.JobQuartz;
import com.mark.demo.batch.listener.MessageProcessListener;
import com.mark.demo.batch.processor.MessageItemProcessor;

/*
*hxp(hxpwangyi@126.com)
*2017年10月4日
*
*/
@Configuration
public class BatchConfiguration {
	@Resource(name="dataSource")
	private DataSource dataSource;
	
	@Resource(name="monitorDataSource")
	private DataSource monitorDataSource;
	
	@Resource(name="bussinesstxManager")
	private PlatformTransactionManager bussinesstxManager;
	@Resource(name="messageProcessJob")
	private Job messageProcessJob;
	
	@Bean
	public ItemReader<Message> reader() {
		JdbcPagingItemReader<Message> reader = new JdbcPagingItemReader<Message>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(100);
        reader.setRowMapper(messageRowMapper());
        reader.setQueryProvider(mySqlPagingQueryProvider());
        return reader;
    }
	
	@Bean
	public RowMapper<Message> messageRowMapper(){
		return new MessageRowMapper();
	}
	
	@Bean
	public PagingQueryProvider mySqlPagingQueryProvider(){
		MySqlPagingQueryProvider provider=new MySqlPagingQueryProvider();
		provider.setSelectClause("messageId,content,receiver,type,receiveTime");
		provider.setFromClause("message");
		Map<String,Order> sortKeys=new HashMap<String,Order>();
		sortKeys.put("messageId", Order.ASCENDING);
		provider.setSortKeys(sortKeys);
		return provider;
	}
	
	@Bean
	public ItemProcessor<Message, ProcessedMessage> messageProcessor(){
		ItemProcessor<Message, ProcessedMessage> processor=new MessageItemProcessor();
		return processor;
	}
	
	@Bean
	public ItemWriter<ProcessedMessage> messageWriter(){
		JdbcBatchItemWriter<ProcessedMessage> writer=new JdbcBatchItemWriter<ProcessedMessage>();
		writer.setDataSource(dataSource);
		writer.setSql("insert into processed_message(messageId,content,receiver,type,receiveTime) values(:messageId,:content,:receiver,:type,:receiveTime)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ProcessedMessage>());
		return writer;
	}
	
	@Bean
	public JobExecutionListener MessageProcessListener(){
		return new MessageProcessListener();
	}
	
	@Bean
	public JobBuilderFactory jobBuilderFactory() throws Exception{
		JobBuilderFactory factory=new JobBuilderFactory(jobRepository());
		return factory;
	}
	
	@Bean("messageProcessJob")
	public Job messageProcessJob(JobBuilderFactory jobs, @Qualifier("step1")Step s1, JobExecutionListener listener) throws Exception{
		return jobs.get("messageJob")
		.incrementer(new RunIdIncrementer()).listener(MessageProcessListener())
		.flow(s1).end().build();
	}
	
	@Bean("jobRepository")
	public JobRepository jobRepository() throws Exception{
		JobRepositoryFactoryBean factory=new JobRepositoryFactoryBean();
		factory.setDataSource(monitorDataSource);
		factory.setTransactionManager(bussinesstxManager);
		return factory.getObject();
	}
	
	@Bean
	public StepBuilderFactory stepBuilderFactory() throws Exception{
		StepBuilderFactory factory=new StepBuilderFactory(jobRepository(),bussinesstxManager);
		return  factory;
	}
	
	@Bean("step1")
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Message> reader,
            ItemWriter<ProcessedMessage> writer, ItemProcessor<Message, ProcessedMessage> processor) {
        return stepBuilderFactory.get("step1")
                .<Message, ProcessedMessage> chunk(200)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .startLimit(3)
                .throttleLimit(50)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws Exception{
		SchedulerFactoryBean scheduler=new SchedulerFactoryBean();
		
		Map<String,Object> jobDetailData=new HashMap<String,Object>();
		jobDetailData.put("name", "test");
		jobDetailData.put("group", "quartz-batch");
		jobDetailData.put("description", "test");
		jobDetailData.put("jobClass", "com.mark.demo.batch.job.JobQuartz");
		JobDataMap jobDataAsMap=new JobDataMap();
		jobDataAsMap.put("jobName", messageProcessJob);
		jobDataAsMap.put("jobRepository", jobRepository());
		jobDetailData.put("jobDataMap", jobDataAsMap);
		jobDetailData.put("durability", true);
		JobDetail jobDetail=JobDetailSupport.newJobDetail(jobDetailData);
		scheduler.setJobDetails(jobDetail);
		
		Trigger trigger=TriggerBuilder.newTrigger().forJob(jobDetail).withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * * * ?")).build();
	
		scheduler.setTriggers(trigger);
		return scheduler;
		
	}
}
