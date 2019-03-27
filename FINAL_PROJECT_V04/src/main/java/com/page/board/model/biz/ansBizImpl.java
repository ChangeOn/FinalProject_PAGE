package com.page.board.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page.board.model.dao.ansDao;
import com.page.board.model.dto.Criteria;
import com.page.board.model.dto.ansDto;
@Service
public class ansBizImpl implements ansBiz {

	@Autowired
	private ansDao adao; 
	@Override
	public List<ansDto> ansList(int boardseq) {
		System.out.println("ansbiz"+boardseq);
		return adao.ansList(boardseq);
	}

	@Override
	public void insertAns(ansDto adto) {
		adao.insertAns(adto);
	}


	@Override
	public void deleteAns(int ansno) {
		adao.deleteAns(ansno);
	}

	@Override
	public List<ansDto> ansPaging(Criteria cri) {
		return adao.ansPaging(cri);
	}

	@Override
	public int countAnsListTotal(int boardseq) {
		return adao.countAnsListTotal(boardseq);
	}
	

}
