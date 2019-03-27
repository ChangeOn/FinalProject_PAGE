package com.page.board.model.dao;

import java.util.List;

import com.page.board.model.dto.Criteria;
import com.page.board.model.dto.ansDto;

public interface ansDao {
	
	String NAMESPACE ="ansBoard.";
	
	public List<ansDto> ansList(int boardseq);
	public List<ansDto> ansPaging(Criteria cri);
	public int countAnsListTotal(int boardseq);
	
	public void insertAns(ansDto adto);

	public void deleteAns(int ansno);

}
