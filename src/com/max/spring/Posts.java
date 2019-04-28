package com.max.spring;

public class Posts {

	private int id;
	private int users_id;
	private String username;
	private int topics_id;
	private String text;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUsers_id() {
		return users_id;
	}
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTopics_id() {
		return topics_id;
	}
	public void setTopics_id(int topics_id) {
		this.topics_id = topics_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
