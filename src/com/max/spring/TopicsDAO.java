package com.max.spring;

import java.util.List;
import javax.sql.DataSource;

public interface TopicsDAO {

	public void setDataSource(DataSource dataSource);
	
	public String create(String name,int sections_id,int users_id);
	
	public Topics getTopics(int id);
	
	public List<Topics> getRecentTopics();
	
	public List<Topics> getAllTopics(int section_id);
	
	public boolean delete(int id,int section_id);
	
	public boolean deleteChild(int id);
	
	public String Validation();
	
}
