package com.mark.demo.security.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mark.demo.security.entity.Role;
import com.mark.demo.security.entity.User;
import com.mark.demo.security.repository.UserRepository;

/*
*hxp(hxpwangyi@126.com)
*2017年9月22日
*
*/
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.getByUserName(userName);
        if(null == user) {    
             throw new UsernameNotFoundException("用户" + userName + "不存在");    
        }         
     
        List<Role> authorities = user.getRoleList(); 
        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();  
        if(authorities!=null){
	        for(Iterator<Role> iter=authorities.iterator();iter.hasNext();){  
	            auths.add(new SimpleGrantedAuthority(iter.next().getRoleName()));  
	        }  
        }
        UserDetails userDetails=new UserDetails() {
			
			@Override
			public boolean isEnabled() {
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return user.getUserName();
			}
			
			@Override
			public String getPassword() {
				return user.getPassword();
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return auths;
			}
		};
        return userDetails;  
              
        
	}

}
