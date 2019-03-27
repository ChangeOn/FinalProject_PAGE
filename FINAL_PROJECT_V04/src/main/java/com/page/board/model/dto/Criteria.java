package com.page.board.model.dto;

public class Criteria {
	// 현재 페이지 번호
	private int page;
	// 한페이지당 보여줄 게시글의 갯수
	private int perPageNum;
	// 댓글페이징의 게시물
	private int boardseq;
	
	// 특정 페이지의 게시글 시작 행 번호
	public int getPageStart() {
		return (this.page-1)*perPageNum+1;
	}
	// 처음 들어왔을때 페이지 기본 세팅
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;	
	}
	
	
	public Criteria(int page, int perPageNum, int boardseq) {
		super();
		this.page = page;
		this.perPageNum = perPageNum;
		this.boardseq = boardseq;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1; 
		}else {
			this.page = page;
		}
	}
	
	public int getPerPageNum() {
		return perPageNum;
	}
	
	public void setPerPageNum(int pageCount) {
		int cnt = this.perPageNum;
		if(pageCount != cnt) {
			this.perPageNum = cnt;
		}else {
			this.perPageNum = pageCount;
		}
	}
	public int getBoardseq() {
		return boardseq;
	}
	public void setBoardseq(int boardseq) {
		this.boardseq = boardseq;
	}
	
	

}
