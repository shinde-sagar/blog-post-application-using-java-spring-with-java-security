package com.max.spring;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class TopicsJDBC implements TopicsDAO {

	private String text;
	private JdbcTemplate jdbctemplate;
	private DataSource dataSource;
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbctemplate = new JdbcTemplate(this.dataSource);
	}

	@Override
	public String create(String name,int sections_id,int users_id) {
		this.text = name;
		String Vresult = Validation();
		if(Vresult != "true") {
			return Vresult;
		}
		String SQL = "insert into topics(`users_id`,`sections_id`,`text`) values(?,?,?)";
		int result = this.jdbctemplate.update(SQL,new Object[] {users_id,sections_id,name});
		if(result == 1) {
			return "true";
		}
		return "error while creating topic";
	}

	@Override
	public Topics getTopics(int id) {
		try {
			String SQL = "select id,text from `topics` where id = ? limit 1";
			Topics topic = (Topics) jdbctemplate.queryForObject(SQL, new Object[] {id}, new TopicsMapping2());
			return topic;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topics> getRecentTopics() {
		String SQL = "SELECT topics.users_id as users_id,topics.id as id,sections.name as sections,topics.text as text FROM `topics`left join `sections` on sections.id = topics.sections_id order by topics.id DESC limit 7";
		List<Topics> topics = jdbctemplate.query(SQL, new TopicsMapping());
		return topics;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topics> getAllTopics(int section_id) {
		String SQL = "SELECT topics.users_id as users_id,topics.id as id,sections.name as sections,topics.text as text FROM `topics`left join `sections` on sections.id = topics.sections_id where sections_id = ?";
		List<Topics> topics = jdbctemplate.query(SQL, new Object[] {section_id} ,new TopicsMapping());
		return topics;
	}
	
	
	
	@Override
	public boolean delete(int id,int section_id) {
		
		if(this.deleteChild(id)) {
			System.out.println("delete from `topics` where id = " + id +" and sections_id = " + section_id);
			String SQL = "delete from `topics` where id = ? and sections_id = ?";
			int result = jdbctemplate.update(SQL,new Object[] {id,section_id});
			if(result == 1) {
				return true;
			}
		}
		return false;
		
	}

	@Override
	public String Validation() {
		Validation validation = new Validation();
		if(!validation.length(this.text, 1, 500))
			return "topic length must be greater than 1 and less than 500";
		if(!validation.isAlphaNumericWithSpace(this.text))
			return "invalid topic text found";
		return "true";
	}

	@Override
	public boolean deleteChild(int id) {
		
		String SQL = "delete from `posts` where topics_id = ?";
		int result = jdbctemplate.update(SQL,new Object[] {id});
		if(result == 1) {
			return true;
		}
		return false;
	}
}
