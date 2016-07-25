package org.newt.demo;

import static com.jayway.restassured.RestAssured.expect;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;
import com.newt.dao.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleCustomersApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:10313")
public class SampleCustomersApplicationTests {
	
	private static Logger log = LoggerFactory.getLogger(SampleCustomersApplicationTests.class);

	@Test
	public void testGetAllCustomers() {
		
		log.info("****************************Entering to testGetAllCustomers*****************************");
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		builder.expectStatusCode(200);
		ResponseSpecification responseSpec = builder.build();
		String url = "http://localhost:10313/customer";
		Response res=expect().spec(responseSpec).when().given().auth().basic("TEST", "test").get(url);
		log.info(res.asString());
		log.info("****************************Exiting from testGetAllCustomers*****************************");
	}
	
	@Test
	public void testFindCustomer() {
		
		log.info("****************************Entering to testFindCustomer*****************************");
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		builder.expectStatusCode(200);
		ResponseSpecification responseSpec = builder.build();
		String url = "http://localhost:10313/customer/123";
		Response res=expect().spec(responseSpec).when().given().auth().basic("TEST", "test").get(url);
		log.info(res.asString());
		log.info("****************************Exiting from testFindCustomer*****************************");
	}
	
	@Test
	public void testCreateCustomers() {
		
		log.info("****************************Entering to testCreateCustomers*****************************");
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		builder.expectStatusCode(415);
		ResponseSpecification responseSpec = builder.build();
		String url = "http://localhost:10313/customer";
		Customer cus=new Customer();
		cus.setAddress("ABC");
		cus.setId(123);
		cus.setName("XXX");
		cus.setPassword("xxxx");
		cus.setusername("us123");
		expect().spec(responseSpec).when().given().auth().basic("TEST", "test").body(cus);
		Response res=expect().spec(responseSpec).when().given().auth().basic("TEST", "test").post(url);
		log.info(res.asString());
		log.info("****************************Exiting from testCreateCustomers*****************************");
	}
	
	@Test
	public void testCreateOrupdateCustomers() {
		
		log.info("****************************Entering to testCreateOrupdateCustomers*****************************");
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		builder.expectStatusCode(415);
		ResponseSpecification responseSpec = builder.build();
		String url = "http://localhost:10313/customer/123";
		Customer cus=new Customer();
		cus.setAddress("ABC");
		cus.setId(123);
		cus.setName("XXX");
		cus.setPassword("xxxx");
		cus.setusername("us123");
		expect().spec(responseSpec).when().given().auth().basic("TEST", "test").body(cus);
		Response res=expect().spec(responseSpec).when().given().auth().basic("TEST", "test").put(url);
		log.info(res.asString());
		log.info("****************************Exiting from testCreateOrupdateCustomers*****************************");
	}
	
	@Test
	public void testDeleteCustomer() {
		
		log.info("****************************Entering to testDeleteCustomer*****************************");
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		builder.expectStatusCode(200);
		ResponseSpecification responseSpec = builder.build();
		String url = "http://localhost:10313/customer/123";
		Response res=expect().spec(responseSpec).when().given().auth().basic("TEST", "test").delete(url);
		log.info(res.asString());
		log.info("****************************Exiting from testDeleteCustomer*****************************");
	}

}
