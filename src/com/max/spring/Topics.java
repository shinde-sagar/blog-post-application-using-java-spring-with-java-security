package com.max.spring;

public class Topics {

	private int id;
	private int users_id;
	private String sections_name;
	private String text;
	private int PostCount;
	
	public int getPostCount() {
		return PostCount;
	}
	public int getUsers_id() {
		return users_id;
	}
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}
	public void setPostCount(int postCount) {
		PostCount = postCount;
	}
	public int getId() {
		return id;
	}
	public String getSections_name() {
		return sections_name;
	}
	public void setSections_name(String sections_name) {
		this.sections_name = sections_name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
