package com.mark.demo.security.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/*
*hxp(hxpwangyi@126.com)
*2017年9月16日
*
*/
@Configuration
public class CaptchaConfiguration {
	
	@Bean
	public DefaultKaptcha producer(){
		DefaultKaptcha kaptcha=new DefaultKaptcha();
		Properties properties=new Properties();
		properties.put("kaptcha.border", "yes");
		properties.put("kaptcha.image.width", 150);
		properties.put("kaptcha.image.height", 40);
		properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
		properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
		properties.put("kaptcha.textproducer.font.size", 30);
		properties.put("kaptcha.textproducer.char.space", 10);
		properties.put("kaptcha.textproducer.font.color", "red");
		properties.put("kaptcha.textproducer.char.string", "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		properties.put("kaptcha.textproducer.char.length", 4);
		
		Config config=new Config(properties);
		kaptcha.setConfig(config);
		
		return kaptcha;
	}
}
