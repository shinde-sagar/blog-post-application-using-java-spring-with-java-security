package com.max.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class TopicsMapping implements ParameterizedRowMapper<Topics>{

	@Override
	public Topics mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Topics topics = new Topics();
		topics.setId(rs.getInt("id"));
		topics.setUsers_id(rs.getInt("users_id"));
		topics.setSections_name(rs.getString("sections"));
		topics.setText(rs.getString("text"));
		
		return topics;
	}
	
}
