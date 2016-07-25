package com.newt.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newt.dao.model.Customer;
//import com.newt.dao.model.CustomerExample;
import com.newt.dao.repository.CustomerMapper;


@Service
public class CustomerDAOImpl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public List<Customer> findAllCustomers() {
		logger.info("Fetch all customers.");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
			return customerMapper.selectAllCustomer();
		} finally {
			// If sqlSession is not closed
			// then database Connection associated this sqlSession will not be
			// returned to pool
			// and application may run out of connections.
			sqlSession.close();
		}
	}

	public Customer findCustomerById(Integer id) {
		logger.debug("Select Customer By ID :{}", id);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
			return customerMapper.selectCustomerById(id);
		} finally {
			sqlSession.close();
		}
	}

	public boolean createCustomer(Customer customer) {
		logger.debug("Create a new customer");
		boolean flag=false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
			customerMapper.insertCustomer(customer);
			sqlSession.commit();
			flag=true;
		} finally {
			sqlSession.close();
		}
		return flag;
	}
	
	public boolean createOrUpdateCustomer(Customer customer) {
		boolean flag=false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
			Customer cus=customerMapper.selectCustomerById(customer.getId());
			if(cus!=null)
				customerMapper.updateCustomer(customer);
			else
				customerMapper.insertCustomer(customer);
			sqlSession.commit();
			flag=true;
		} finally {
			sqlSession.close();
		}
		return flag;
	}
	
	public boolean deleteCustomerById(Integer id) {
		boolean flag=false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
			customerMapper.deleteCustomer(id);
			flag=true;
			sqlSession.commit();
			
		} finally {
			sqlSession.close();
		}
		return flag;
	}
}
