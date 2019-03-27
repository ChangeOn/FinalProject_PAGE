package com.page.cal.model.dto;

public class CalDto {

	private String seq;
	private String id;
	private String title;
	private String content;
	private String startdate;
	private String enddate;
	
	public CalDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalDto(String seq, String id, String title, String content, String startdate, String enddate) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.startdate = startdate;
		this.enddate = enddate;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	


}
