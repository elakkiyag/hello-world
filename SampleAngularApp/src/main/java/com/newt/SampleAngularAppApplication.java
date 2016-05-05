package com.newt;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.newt.service.TodoService;

@SpringBootApplication
@EnableTransactionManagement
public class SampleAngularAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SampleAngularAppApplication.class);
	}

	public static void main(String[] args) throws IOException {
		ApplicationContext appContext = SpringApplication.run(SampleAngularAppApplication.class, args);
		TodoService todoService = appContext.getBean(TodoService.class);
		String filePath = (args.length > 0) ? args[0] : "etc/todo.txt";
		todoService.loadTodos(filePath);
	}
}
