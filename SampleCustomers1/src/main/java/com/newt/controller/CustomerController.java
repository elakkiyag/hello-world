package com.newt.controller;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.newt.dao.model.Customer;
import com.newt.exception.CustomerException;
import com.newt.rabbit.ConsumeMessage;
import com.newt.service.CustomerService;

@RestController
@EnableCircuitBreaker
@EnableTurbine
public class CustomerController {

	private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ConsumeMessage consumeMsg;

	String customerId = null;

	@RequestMapping(value = "/customer", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@HystrixCommand(fallbackMethod = "findAllCustomersFail")
	public List<Customer> getAllCustomers() {
		logger.info("Get all customers.");

		try {
			Future<List<Customer>> result = customerService.findAllCustomers();
			return result.get(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			logger.error("Exception in CustomerController:findAllCustomers:" + e);
		}
		return null;
	}

	public List<Customer> findAllCustomersFail() {
		logger.info("Customer fetching failed.");
		return null;
	}

	@RequestMapping(value = "/rabbit", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	public Customer findCustomerFromRabbit() {
		logger.info("Find the Customer by Customer ID.");
		try {
			customerId = consumeMsg.consume();
		} catch (Exception e1) {
			System.out.println("Exp in Receivng msg----=== " + e1);
		}
		try {
			logger.info("Find the Customer ID = " + customerId);
			Future<Customer> result = customerService.findCustomerById(Integer.parseInt(customerId));
			return (Customer) result.get();
		} catch (Exception e) {
			logger.error("Exception in CustomerController:findAllCustomers:" + e);
			// TODO: handle exception
		}
		return null;
	}

	@RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@HystrixCommand(fallbackMethod = "findCustomerByIdFail")
	public Customer findCustomer(@PathVariable Integer customerId) {
		logger.info("Find the Customer by Customer ID.");
		if (customerId < 0) {
			logger.error("Invalid customer ID passed. Id = " + customerId);
			throw new CustomerException(new FieldError("CustomerService", "CustomerId", "Invalid Customer value"));
		}

		try {
			logger.info("Find the Customer ID = " + customerId);
			Future<Customer> result = customerService.findCustomerById(customerId);
			return result.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Exception in CustomerController:findAllCustomers:" + e);
			// TODO: handle exception
		}
		return null;
	}

	public Customer findCustomerByIdFail(Integer customerId) {

		return null;
	}

	@RequestMapping(value = "/customer/{customerId}", method = RequestMethod.DELETE, produces = { "application/json",
			"application/xml" })
	@HystrixCommand(fallbackMethod = "deleteCustomerByIdFail")
	public Boolean deleteCustomer(@PathVariable Integer customerId) {
		logger.info("Delete customer by ID");
		if (customerId < 0) {
			logger.error("Invalid customer ID passed. Id = " + customerId);
			throw new CustomerException(new FieldError("Invalid Customer Input", "Error", "Error"));
		}
		try {
			logger.info("Delete the Customer ID = " + customerId);
			Future<Boolean> result = customerService.deleteCustomerById(customerId);
			return result.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Exception in CustomerController:findAllCustomers:" + e);
			// TODO: handle exception
		}
		return null;
	}

	public Boolean deleteCustomerByIdFail(Integer customerId) {

		return false;
	}

	@Transactional
	@RequestMapping(value = "/customer", method = RequestMethod.POST, consumes = { "application/json",
			"application/xml" }, produces = { "application/json", "application/xml" })
	@HystrixCommand(fallbackMethod = "createCustomerFail")
	public Boolean createCustomers(@RequestBody Customer customer) {

		logger.info("Entering CustomerController: createCustomers");
		if (customer == null) {
			logger.error("Invalid customer is passed.");
			throw new CustomerException(new FieldError("Invalid Customer Input", "Error", "Error"));
		}

		try {
			Future<Boolean> result = customerService.createCustomer(customer);
			return result.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Exception in CustomerController:findAllCustomers:" + e);
			// TODO: handle exception
		}
		return null;
	}

	public Boolean createCustomerFail(Customer customer) throws InterruptedException {

		return false;
	}

	@Transactional
	@RequestMapping(value = "/customer/{customerId}", method = RequestMethod.PUT, consumes = { "application/json",
			"application/xml" }, produces = { "application/json", "application/xml" })
	@HystrixCommand(fallbackMethod = "createOrUpdateCustomerFail")
	public Boolean createOrupdateCustomers(@RequestBody Customer customer) {

		logger.info("Entering CustomerController: createCustomers");
		if (customer == null) {
			logger.error("Invalid customer ID passed.");
			throw new CustomerException(new FieldError("Invalid Customer Input", "Error", "Error"));
		}

		try {
			Future<Boolean> result = customerService.createOrUpdateCustomer(customer);
			return result.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Exception in CustomerController:findAllCustomers:" + e);
			// TODO: handle exception
		}
		return null;
	}

	public Boolean createOrUpdateCustomerFail(Customer customer) throws InterruptedException {

		return false;
	}

}
