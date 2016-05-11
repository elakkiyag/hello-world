package com.newt.dao.model;

import java.io.Serializable;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

 
@Entity
public class UserDetail implements Serializable{
 
 /**
	 * 
	 */
	private static final long serialVersionUID = 7681382090150416665L;
@Id
 private long userId;
 private String username;
 private String password;
 private String roles;
 
 public String getUsername() {
  return username;
 }
 
 public void setUsername(String username) {
  this.username = username;
 }
 
 public String getPassword() {
  return password;
 }
 
 public void setPassword(String password) {
  this.password = password;
 }
 
 public long getUserId() {
  return userId;
 }
 
 public void setUserId(long userId) {
  this.userId = userId;
 }
 


public String getRoles() {
	return roles;
}

public void setRoles(String roles) {
	this.roles = roles;
}
 
}