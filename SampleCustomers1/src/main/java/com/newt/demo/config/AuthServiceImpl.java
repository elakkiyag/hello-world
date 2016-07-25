package com.newt.demo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;



import com.newt.dao.model.UserDetail;
import com.newt.dao.repository.UserDetailRepository;


@Service
@Component
public class AuthServiceImpl  implements  UserDetailsService {

	
	 @Autowired
	 private  UserDetailRepository userDAO;
	 
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetail userDetails =  userDAO.getUserInfo(username);		
		  SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userDetails.getRoles());		 		  
		  UserDetails user = new User(userDetails.getUsername(),
				  userDetails.getPassword(), true, true, true, true, Arrays.asList(authority));
		  return user;	        

	        
	}

}
