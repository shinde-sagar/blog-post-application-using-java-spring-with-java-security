package com.max.spring;

import java.util.List;

import javax.sql.DataSource;


public interface SectionsDAO {

	public void setDataSource(DataSource dataSource);
	
	public String create(String name,int users_id);
	
	public List<Sections> getAllSections();
	
	public boolean delete(int id);
	
	public String Validation();
	
	public boolean isSectionExist();
	
}
