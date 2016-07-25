package com.newt.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(Ordered.LOWEST_PRECEDENCE - 8)
public class TodoSecurity extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/rest/todos*/**")
				.permitAll().antMatchers("/rest/**")
				.authenticated().anyRequest().permitAll();
	}
}
