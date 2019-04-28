package com.max.spring;

import java.util.List;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class PostsJDBC implements PostsDAO {
	
	private JdbcTemplate jdbctemplate;
	private DataSource dataSource;
	private String text;
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbctemplate = new JdbcTemplate(this.dataSource);
	}
	
	@Override
	public String create(int topic_id,String name,int users_id) {
		System.out.println("name  : " + name);
		this.text = name;
		String Vresult = this.Validation();
		if(Vresult != "true"){
			return Vresult;
		}
		String SQL = "insert into posts(`users_id`,`topics_id`,`text`) values(?,?,?)";
		int result = this.jdbctemplate.update(SQL,new Object[] {users_id,topic_id,name});
		if(result == 1) {
			return "true";
		}
		return "error while creating post";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Posts> getAllPosts(int topic_id) {
		String SQL = "select posts.id as id,posts.users_id,posts.text,users.username from `posts` left join users on users.id = posts.users_id where topics_id = ?";
		List<Posts> post =  this.jdbctemplate.query(SQL,new Object[] {topic_id},new PostsMapping());
		return post;
	}
	
	@Override
	public boolean delete(int id) {
		String SQL = "delete from `posts` where id = ?";
		int result =  this.jdbctemplate.update(SQL,new Object[] {id});
		if(result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public String Validation() {
		Validation validation = new Validation();
		if(!validation.length(this.text, 1, 500))
			return "post length must be greater than 1 and less than 500";
		if(!validation.isAlphaNumericWithSpace(this.text))
			return "invalid post text found";
		return "true";
	}

	@Override
	public String update(int id, int topic_id, String post_txt) {
		this.text = post_txt;
		String Vresult = this.Validation();
		if(Vresult != "true"){
			return Vresult;
		}
		System.out.println("update posts set text = "+ post_txt +" where id = " + id + " and topics_id = " + topic_id);
		String SQL = "update posts set text = ? where id = ? and topics_id = ?";
		int result = this.jdbctemplate.update(SQL,new Object[] {post_txt,id,topic_id});
		if(result == 1) {
			return "true";
		}
		return "failed to update post";
	}
	
	
}
