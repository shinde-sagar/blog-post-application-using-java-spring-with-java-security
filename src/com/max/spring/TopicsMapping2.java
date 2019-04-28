package com.max.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class TopicsMapping2 implements ParameterizedRowMapper<Topics>{

	@Override
	public Topics mapRow(ResultSet rs, int rowNum) throws SQLException {
		Topics topics = new Topics();
		topics.setId(rs.getInt("id"));
		topics.setText(rs.getString("text"));
		return topics;
	}
	
}
