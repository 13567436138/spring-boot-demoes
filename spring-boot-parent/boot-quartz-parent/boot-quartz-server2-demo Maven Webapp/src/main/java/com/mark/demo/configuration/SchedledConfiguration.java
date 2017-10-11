package com.mark.demo.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.quartz.Trigger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.expression.ParseException;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.mark.demo.job.TestJob;

/*
*hxp(hxpwangyi@126.com)
*2017年10月11日
*
*/
@Configuration
public class SchedledConfiguration {
	
	@Bean(name = "detailFactoryBean")
    public JobDetailFactoryBean detailFactoryBean(){
		JobDetailFactoryBean bean = new JobDetailFactoryBean ();
        bean.setJobClass(TestJob.class);
        bean.setDurability(true);
        bean.setName("testJob");
        return bean;
    }

    @Bean(name = "cronTriggerBean")
    public CronTriggerFactoryBean cronTriggerBean(JobDetailFactoryBean detailFactoryBean){
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean ();
        tigger.setJobDetail (detailFactoryBean.getObject ());
        try {
            tigger.setCronExpression ("0/5 * * * * ? ");//每5秒执行一次
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return tigger;

    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean[] cronTriggerBeans) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setExposeSchedulerInRepository(true);
        factory.setConfigLocation(new ClassPathResource("quartz.properties"));
        Trigger[] triggers=new Trigger[cronTriggerBeans.length];
    	for(int i=0;i<cronTriggerBeans.length;i++){
    		triggers[i]=cronTriggerBeans[i].getObject();
    	}
    	factory.setTriggers(triggers);
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        return propertiesFactoryBean.getObject();
    }
    
    /*@Bean
    public QuartzInitializerListener executorListener() {
       return new QuartzInitializerListener();
    }*/
}
