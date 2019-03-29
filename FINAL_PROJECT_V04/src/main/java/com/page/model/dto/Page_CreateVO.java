package com.page.model.dto;

public class Page_CreateVO {
	
	private int page_create_no;
	private int user_no;
	private int page_no;
	
	public int getPage_create_no() {
		return page_create_no;
	}
	public void setPage_create_no(int page_create_no) {
		this.page_create_no = page_create_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	
	@Override
	public String toString() {
		
		return "page_create_no: " + page_create_no
				+" user_no: " + user_no
				+" page_no: " + page_no;
	}

}
