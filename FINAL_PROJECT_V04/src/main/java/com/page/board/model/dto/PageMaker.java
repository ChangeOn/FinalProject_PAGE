package com.page.board.model.dto;

public class PageMaker {
	
	private Criteria cri; // Criteria Dto
	private int totalCount; // 전체 게시글 카운트
	private int startPage; // 시작 페이징 숫자
	private int endPage; // 끝 페이징 숫자
	private boolean prev; // 이전 버튼
	private boolean next; // 다음 버튼
	private int displayPageNum = 5; // 화면에 보여질 페이지 갯수
	
	public Criteria getCri() {
		return cri;
	}
	public  void setCri(Criteria cri) {
		this.cri = cri;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData(); // 페이징 관련 버튼 계산
	}
	// 페이징 버튼 생성 계산식
	public void calcData() {
		// 화면에 보여질 끝 페이지 번호 = 소숫점 올림(현재 페이지 번호/ 화면에 보여질 페이지 갯수)
		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum)*displayPageNum);
		// 마지막 페이지 번호 
		int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		// 화면에 보여질 시작 페이지 번호= (끝 페이지 번호 - 화면에 보여질 페이지 번호 갯수 ) +1
		startPage = (endPage - displayPageNum) +1;
		if(startPage <= 0) startPage = 1;
		
		// 시작 페이지가 1 아니면 <이전>버튼 생성 됨
		prev = startPage == 1? false : true ;
		// 총 게시글 수 가 현재 페이지당 개시글 수 보다 많으면 <다음> 버튼 생성								
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}

	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplauPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	
	
	
	
	
}
