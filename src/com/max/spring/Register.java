package com.max.spring;

import javax.sql.DataSource;

public interface Register {
	
	public String CreateAccount(String username,String password);
	
	public boolean CreateUser();
	
	public boolean UserExist();
	
	public String ValidateUser();

	public boolean AttachRole();
	
	public void setDataSource(DataSource dataSource);
	
}
