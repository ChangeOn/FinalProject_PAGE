package com.page.board.model.dto;

import java.util.Date;

public class ansDto {

	private int boardseq;
	private int ansno;
	private String id;
	private String anscontent;
	private Date ansregdate;
	
	public ansDto() {

	} 

	public int getBoardseq() {
		return boardseq;
	}
	public void setBoardseq(int boardseq) {
		this.boardseq = boardseq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAnsno() {
		return ansno;
	}
	public void setAnsno(int ansno) {
		this.ansno = ansno;
	}
	public String getAnscontent() {
		return anscontent;
	}
	public void setAnscontent(String anscontent) {
		this.anscontent = anscontent;
	}
	public Date getAnsregdate() {
		return ansregdate;
	}
	public void setAnsregdate(Date ansregdate) {
		this.ansregdate = ansregdate;
	}
	
	
	
}
