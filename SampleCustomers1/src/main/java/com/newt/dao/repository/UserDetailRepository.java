package com.newt.dao.repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.newt.dao.model.UserDetail;




@Repository
public class UserDetailRepository  {	
	
	 
		  private JdbcTemplate jdbcTemplate;
		  
		    @Autowired
		    public void setDataSource(DataSource dataSource) {
		        this.jdbcTemplate = new JdbcTemplate(dataSource);
		    }
		    
		    public UserDetail getUserInfo(String username){
		    	String sql = "SELECT u.username name,u.roles roles, u.password pass,  "+
		    			     "UserDetail u  WHERE "+
		    			     "u.username = ?";
		    	UserDetail userInfo = (UserDetail)jdbcTemplate.queryForObject(sql, new Object[]{username},
		    		new RowMapper<UserDetail>() {
			            public UserDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	UserDetail user = new UserDetail();
			                user.setUsername(rs.getString("name"));
			                user.setPassword(rs.getString("pass"));
			                user.setRoles(rs.getString("roles"));
			                return user;
			            }
		        });
		    	return userInfo;
		    }
	}


