package com.max.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class SectionsMapping implements ParameterizedRowMapper<Sections>{

	@Override
	public Sections mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Sections sections = new Sections();
		sections.setId(rs.getInt("id"));
		sections.setName(rs.getString("name"));
		return sections;
	}
	
}
