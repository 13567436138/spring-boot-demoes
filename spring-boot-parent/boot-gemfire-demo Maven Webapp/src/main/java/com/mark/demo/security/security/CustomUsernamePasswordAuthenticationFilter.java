package com.mark.demo.security.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mark.demo.security.entity.User;
import com.mark.demo.security.repository.UserRepository;
import com.mark.demo.security.session.RedisSessionManager;
import com.mark.demo.security.session.RedisSessionManager.SessionKey;
import com.mark.demo.security.utils.StringUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年9月24日
*
*/

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	public static final String VALIDATE_CODE = "captcha";  
    public static final String USERNAME = "userName";  
    public static final String PASSWORD = "password";
    private UserRepository userRepository;
	private RedisSessionManager redisSessionManager;

	

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setRedisSessionManager(RedisSessionManager redisSessionManager) {
		this.redisSessionManager = redisSessionManager;
	}

	@Override
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override  
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {  
        if (!request.getMethod().equals("POST")) {  
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());  
        }  
        //检测验证码  
        checkValidateCode(request);  
          
        String username = obtainUsername(request);  
        String password = obtainPassword(request);  
          
        //验证用户账号与密码是否对应  
        username = username.trim();  
          
        User user = userRepository.getByUserName(username);
          
        if(user == null || !user.getPassword().equals(password)) {  
            throw new AuthenticationServiceException("用户名或者密码错误！");   
        }  
          
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);  
          
        setDetails(request, authToken);  
          
        return this.getAuthenticationManager().authenticate(authToken);  
    }  
      
    protected void checkValidateCode(HttpServletRequest request) {   
        String capatcha= redisSessionManager.getString(request, SessionKey.CAPTCHA);
        
        String validateCode = obtainValidateCode(request);    
        if (StringUtils.isEmpty(validateCode) || !capatcha.equalsIgnoreCase(validateCode)) {    
            throw new AuthenticationServiceException("验证码错误！");    
        }    
    }  
      
    private String obtainValidateCode(HttpServletRequest request) {  
        Object obj = request.getParameter(VALIDATE_CODE);  
        return null == obj ? "" : obj.toString();  
    }  
  
  
    @Override  
    protected String obtainUsername(HttpServletRequest request) {  
        Object obj = request.getParameter(USERNAME);  
        return null == obj ? "" : obj.toString();  
    }  
  
    @Override  
    protected String obtainPassword(HttpServletRequest request) {  
        Object obj = request.getParameter(PASSWORD);  
        return null == obj ? "" : obj.toString();  
    }
}
