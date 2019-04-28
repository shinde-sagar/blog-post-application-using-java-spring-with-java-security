package com.max.spring;

import java.util.List;

import javax.sql.DataSource;

public interface PostsDAO {

	public void setDataSource(DataSource dataSource);
	
	public String create(int topic_id,String name,int users_id);
	
	public List<Posts> getAllPosts(int topic_id);
	
	public String update(int id,int topic_id,String post_txt);
	
	public boolean delete(int id);
	
	public String Validation();
	
}
