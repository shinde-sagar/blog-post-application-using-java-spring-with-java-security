package com.max.spring;

public class Validation {

	public Boolean length(String data,int min,int max)
	{
		int len = data.length();
		if(len >= min && len <= max)
		{
			return true;
		}
		return false;
	}
	
	public Boolean isAlphaNumericWithSpace(String data)
	{
		String pattern = "^[a-zA-Z0-9 ]*$";
		return data.matches(pattern);
	}
	
	public Boolean isAlphaNumericOnly(String data)
	{
		String pattern = "^[a-zA-Z0-9]*$";
		return data.matches(pattern);
	}
	
	public Boolean isAlphabeticOnly(String data)
	{
		String pattern = "^[a-zA-Z]*$";
		return data.matches(pattern);
	}
	
	public Boolean isAlphabeticWithSpace(String data)
	{
		String pattern = "^[a-zA-Z ]*$";
		return data.matches(pattern);
	}
	
	public Boolean isNumericOnly(String data)
	{
		String pattern = "^[0-9]*$";
		return data.matches(pattern);
	}
	
}
