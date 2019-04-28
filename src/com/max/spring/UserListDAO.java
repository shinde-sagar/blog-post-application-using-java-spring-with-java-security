package com.max.spring;

import java.util.List;

import javax.sql.DataSource;

public interface UserListDAO {

	public void setDataSource(DataSource dataSource);
	
	public List<Users> getAllUser(); 
	
	public String ChangePassword(String username,String old_password,String new_password);
}
