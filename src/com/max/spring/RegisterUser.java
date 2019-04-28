package com.max.spring;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class RegisterUser implements Register {

	private JdbcTemplate jdbctemplate;
	private DataSource dataSource;
	private String username;
	private String password;
	private int lastId;
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbctemplate = new JdbcTemplate(this.dataSource);
	}
	
	
	@Override
	public String CreateAccount(String username, String password) {
		this.username = username;
		this.password = password;
		String result = this.ValidateUser();
		if(result != "true"){
			return result;
		}
		if(!this.UserExist()){
			this.CreateUser();
			this.AttachRole();
			return "true";
		}
		return "user already exist";
	}
	
	
	@Override
	public boolean CreateUser() {
		String SQL = "insert into `users` (username,password) values(?,?)";
		int result = jdbctemplate.update(SQL,new Object[] {this.username,this.password});
		if(result == 1){
			this.lastId = (int) jdbctemplate.queryForInt("SELECT id FROM `users` ORDER BY id DESC limit 1");
			return true;
		}
		return false;
	}

	@Override
	public boolean UserExist() {
		
		String SQL = "select count(id) from `users` where username = '" + this.username +"'";
		int rowCount = jdbctemplate.queryForInt(SQL);
		if(rowCount > 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public String ValidateUser() {
		Validation validate = new Validation();
		if(!validate.isAlphaNumericOnly(this.username))
			return "invalid username found";
		if(!validate.length(this.username, 3, 32))
			return "username must be greater than 3";
		if(!validate.length(this.password, 8, 16))
			return "password must be greate than 8 and less than 16";
		return "true";
	}


	@Override
	public boolean AttachRole() {
		//default user role attach
		int user_role_id = 1;
		String SQL = "insert into `users_roles` (users_id,roles_id) values(?,?)";
		int result = jdbctemplate.update(SQL,new Object[] {this.lastId,user_role_id});
		if(result == 1){
			return true;
		}
		return false;
	}
	
}
