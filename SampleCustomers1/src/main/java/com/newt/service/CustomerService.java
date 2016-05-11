package com.newt.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.newt.dao.CustomerDAOImpl;
import com.newt.dao.model.Customer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Service
public class CustomerService {

	Logger log = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerDAOImpl customerDAO;

	@Async
	public Future<List<Customer>> findAllCustomers() {

		try {
			// Thread.sleep(500);
			countMillion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new AsyncResult<List<Customer>>(customerDAO.findAllCustomers());
	}

	@Async
	public Future<Customer> findCustomerById(Integer customerId) {
		try {
			// Thread.sleep(500);
			countMillion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new AsyncResult<Customer>(customerDAO.findCustomerById(customerId));
	}

	@Async
	public Future<Boolean> createCustomer(Customer customer) throws InterruptedException {

		// Thread.sleep(500);
		// countMillion();
		Boolean result = customerDAO.createCustomer(customer);
		Thread.sleep(1000);
		return new AsyncResult<Boolean>(result);
	}

	@Async
	public Future<Boolean> createOrUpdateCustomer(Customer customer) throws Exception {

		// Thread.sleep(500);
		// countMillion();
		Boolean result = customerDAO.createOrUpdateCustomer(customer);
		Thread.sleep(10000);
		return new AsyncResult<Boolean>(result);
	}

	@Async

	public Future<Boolean> deleteCustomerById(Integer customerId) {

		try {
			// Thread.sleep(500);
			countMillion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new AsyncResult<Boolean>(customerDAO.deleteCustomerById(customerId));
	}

	@Async
	public Boolean deleteCustomerByIdFail(Integer customerId) {

		try {
			// Thread.sleep(500);
			countMillion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerDAO.deleteCustomerById(null);
	}

	public void countMillion() {
		for (int i = 0; i < 100000; i++) {
			log.debug("Current i is " + i);
		}
	}

}
