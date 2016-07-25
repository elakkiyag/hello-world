/**
 * 
 */
package com.newt.dao.util;

import java.io.*;


import javax.sql.DataSource;


import org.apache.ibatis.session.*;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author narendhirak
 *
 */
@Configuration
public class MyBatisSqlSessionFactory {
	private  SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	@Qualifier("sampleCustomerDS")
	private   DataSource sampleCustomerDS;
	
	@Bean
	public  SqlSessionFactory getSqlSessionFactory() {
		if(sqlSessionFactory==null) {
			try {
				final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean(); 
				sqlSessionFactory.setDataSource(sampleCustomerDS);
				sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
				sqlSessionFactory.setFailFast(true);
				
				//Resource[] mapperResource = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources("classpath:/mapper/*.xml");
		        //sqlSessionFactory.setMapperLocations(mapperResource);
		        return sqlSessionFactory.getObject();
			} catch (IOException e) {
				throw new RuntimeException(e.getCause());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sqlSessionFactory;
	}
	public  SqlSession openSession() {
		return getSqlSessionFactory().openSession();
	}
}
