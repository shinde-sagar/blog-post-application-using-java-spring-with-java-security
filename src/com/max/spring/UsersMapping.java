package com.max.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class UsersMapping implements ParameterizedRowMapper<Users> {

	@Override
	public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
		Users users = new Users();
		users.setId(rs.getInt("id"));
		users.setRole(rs.getInt("roles_id"));
		users.setUsername(rs.getString("username"));
		users.setPassword(rs.getString("password"));
		return users;
	}

	
}
