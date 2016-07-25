package com.newt.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
public class ApplicationConfiguration {	


	@Bean	
	@ConfigurationProperties(prefix="samplecustomer.datasource")
	public DataSource sampleCustomerDS(){
		
		return DataSourceBuilder.create().build();
	}
	
	

}
