package com.max.spring;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class SectionsJDBC implements SectionsDAO {

	private String name;
	private JdbcTemplate jdbctemplate;
	private DataSource dataSource;
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbctemplate = new JdbcTemplate(this.dataSource);
	}

	@Override
	public String create(String name,int users_id) {
		this.name = name;
		String Vresult = Validation();
		if(Vresult != "true") {
			return Vresult;
		}
		if(isSectionExist()) {
			return "Section already Exist";
		}
		String SQL = "insert into sections(`users_id`,`name`) values(?,?)";
		int result = this.jdbctemplate.update(SQL,new Object[] {users_id,name});
		if(result == 1) {
			return "true";
		}
		return "error while creating section";
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Sections> getAllSections() {
		String SQL = "select id,name from `sections`";
		List<Sections> sections = this.jdbctemplate.query(SQL, new SectionsMapping());
		return sections;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(int id) {
		try {
			String SQL = "select id,text from topics where sections_id = ?";
			List<Topics> topics = this.jdbctemplate.query(SQL,new Object[] {id}, new TopicsMapping2());
			for(Topics topic: topics) {
				SQL = "delete from `posts` where topics_id = ?";
				this.jdbctemplate.update(SQL,new Object[] {topic.getId()});
			}
			//System.out.println("topic id : " + topics_id);
			SQL = "delete from `topics` where sections_id = ?";
			this.jdbctemplate.update(SQL,new Object[] {id});
			SQL = "delete from `sections` where id = ?";
			this.jdbctemplate.update(SQL,new Object[] {id});
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public String Validation() {
		Validation validation = new Validation();
		if(!validation.length(this.name, 1, 500))
			return "section length must be greater than 1 and less than 500";
		if(!validation.isAlphaNumericWithSpace(this.name))
			return "invalid section name found";
		return "true";
	}

	@Override
	public boolean isSectionExist() {
		try {
			String SQL = "select id from sections where name = ? limit 1";
			int rowCount = jdbctemplate.queryForInt(SQL,new Object[] {this.name});
			return true;
		}
		catch (EmptyResultDataAccessException e) {
			return false;
		}
		
	}
	
	
	
	
}
