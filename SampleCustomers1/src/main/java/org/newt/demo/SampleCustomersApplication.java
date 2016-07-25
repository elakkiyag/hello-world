package org.newt.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.swagger2.annotations.EnableSwagger2;




@SpringBootApplication
@ComponentScan("com.newt")
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableHystrix
@EnableSwagger2
@EnableAutoConfiguration

@EnableAsync
public class SampleCustomersApplication {


	public static void main(String[] args) {
		SpringApplication.run(SampleCustomersApplication.class, args);
	}

}

