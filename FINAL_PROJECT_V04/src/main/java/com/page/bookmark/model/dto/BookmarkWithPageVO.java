package com.page.bookmark.model.dto;

public class BookmarkWithPageVO {

	int bookmark_no;
	int page_no;
	String bookmark_name;
	int bookmark_scrolltop;
	private String page_content;
	
	public int getBookmark_no() {
		return bookmark_no;
	}
	public void setBookmark_no(int bookmark_no) {
		this.bookmark_no = bookmark_no;
	}
	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public String getBookmark_name() {
		return bookmark_name;
	}
	public void setBookmark_name(String bookmark_name) {
		this.bookmark_name = bookmark_name;
	}
	public int getBookmark_scrolltop() {
		return bookmark_scrolltop;
	}
	public void setBookmark_scrolltop(int bookmark_scrolltop) {
		this.bookmark_scrolltop = bookmark_scrolltop;
	}
	public String getPage_content() {
		return page_content;
	}
	public void setPage_content(String page_content) {
		this.page_content = page_content;
	}
	
	@Override
	public String toString() {
		
		return "bookmark_no: " + bookmark_no
				+" page_no: " + page_no
				+" bookmark_name: " + bookmark_name
				+" bookmark_scrolltop: " + bookmark_scrolltop
				+" page_content: " + page_content;
	}
	
}
