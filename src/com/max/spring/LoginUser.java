package com.max.spring;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginUser implements Login {

	private JdbcTemplate jdbctemplate;
	private DataSource dataSource;
	private HttpSession sessions;
	private String username;
	private String password;
	private final String login_error = "invalid username or password found";
	
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbctemplate = new JdbcTemplate(this.dataSource);
	}
	
	@Override
	public String AttempLogin(String username,String password,HttpSession session) {
		this.username = username;
		this.password = password;
		this.sessions = session;
		
		String result = this.Validation();
		if(result != "true"){
			return result;
		}
		if(!this.CheckCredentials())
			return login_error;
		return "true";
	}

	@Override
	public boolean CheckCredentials() {
		try {
			Users user = (Users) jdbctemplate.queryForObject("select users.id,roles_id,users.username,users.password from `users_roles` left join users ON users.id = users_roles.users_id where users.username = ? and users.password = ? limit 1",new Object[] {this.username,this.password},new UsersMapping());
			this.sessions.setAttribute("user", user);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public String Validation() {
		Validation validate = new Validation();
		if(!validate.isAlphaNumericOnly(this.username) || !validate.length(this.username, 3, 32) || !validate.length(this.password, 8, 16))
			return login_error;
		return "true";
	}

}
