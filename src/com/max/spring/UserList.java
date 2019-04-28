package com.max.spring;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserList implements UserListDAO {

	private JdbcTemplate jdbctemplate;
	private DataSource dataSource;
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbctemplate = new JdbcTemplate(this.dataSource);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getAllUser() {
		String SQL = "select id,username from users";
		List<Users> users =  this.jdbctemplate.query(SQL, new UsersMapper());
		return users;
	}

	@Override
	public String ChangePassword(String username,String old_password,String new_password) {
		try {
			System.out.println("select password from users where username = " + username +" limit 1");
			String SQL = "select password from users where username = ? limit 1";
			Users user = (Users) this.jdbctemplate.queryForObject(SQL, new Object[] {username}, new UsersMapper2());
			if(user.getPassword() == old_password) {
				return "invalid Password found";
			}
			SQL = "update users set password = ? where username = ?";
			int result = this.jdbctemplate.update(SQL, new Object[] {new_password,username});
			if(result == 1) {
				return "true";
			}
			return "failed to change password";
		}
		catch (EmptyResultDataAccessException e) {
			return "invalid user found";
		}
	}
	
}
