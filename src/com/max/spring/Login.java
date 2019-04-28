package com.max.spring;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public interface Login {
	
	public boolean CheckCredentials();
	
	public String Validation();
	
	public void setDataSource(DataSource dataSource);

	public String AttempLogin(String username, String password, HttpSession session);
	
}
