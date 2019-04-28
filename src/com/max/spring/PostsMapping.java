package com.max.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PostsMapping implements ParameterizedRowMapper<Posts>{

	@Override
	public Posts mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Posts posts = new Posts();
		posts.setId(rs.getInt("id"));
		posts.setUsers_id(rs.getInt("users_id"));
		posts.setText(rs.getString("text"));
		posts.setUsername(rs.getString("username"));
		return posts;
	}
	
}
