package com.page.model.dto;

public class PageWithUserVO {
	
	private int page_no;
	private int user_no;
	private String page_name;
	private String page_content;
	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getPage_name() {
		return page_name;
	}
	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}
	public String getPage_content() {
		return page_content;
	}
	public void setPage_content(String page_content) {
		this.page_content = page_content;
	}
	
	@Override
	public String toString() {
		
		return "page_no: " + page_no
				+" user_no: " + user_no
				+" page_name: " + page_name
				+" page_content: " + page_content;
	}

}
