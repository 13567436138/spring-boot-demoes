package com.mark.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.mark.demo.security.security.CustomFilterSecurityInterceptor;
import com.mark.demo.security.security.CustomLogoutSuccessHandler;
import com.mark.demo.security.security.CustomSavedRequestAwareAuthenticationSuccessHandler;
import com.mark.demo.security.security.CustomUserDetailsService;

/*
*hxp(hxpwangyi@126.com)
*2017年9月24日
*
*/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomFilterSecurityInterceptor customFilterSecurityInterceptor;
	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	@Autowired
	private CustomSavedRequestAwareAuthenticationSuccessHandler customSavedRequestAwareAuthenticationSuccessHandler;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http  
		 	.addFilterBefore(customFilterSecurityInterceptor, FilterSecurityInterceptor.class)
	        .authorizeRequests()  
	        .antMatchers("/js/**").permitAll()
	        .antMatchers("/css/**").permitAll()
	        .antMatchers("/common/login*").permitAll()
	        .antMatchers("/captcha*").permitAll()
	        .anyRequest().authenticated() 
	        .antMatchers("/admins/**").hasAuthority("ROLE_ADMIN")
	        .and()  
	        .formLogin()  
	        .loginPage("/common/login")  
	        .permitAll()  
	        .successHandler(customSavedRequestAwareAuthenticationSuccessHandler) 
	        .and()  
	        .logout()  
	        .logoutSuccessUrl("/logout")
	        .logoutSuccessHandler(customLogoutSuccessHandler)
	        .permitAll()  
	        .invalidateHttpSession(true)  
	        .and()  
	        .rememberMe()
	        .tokenValiditySeconds(1209600); 
	}
	
	@Override  
    public AuthenticationManager authenticationManagerBean() throws Exception {  
		return super.authenticationManagerBean();  
    }  

	@Autowired  
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {     
		auth.userDetailsService(customUserDetailsService);  
        auth.eraseCredentials(false);  
      
    } 
	
	
}
