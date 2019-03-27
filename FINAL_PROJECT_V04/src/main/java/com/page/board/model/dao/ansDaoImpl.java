package com.page.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.page.board.model.dto.Criteria;
import com.page.board.model.dto.ansDto;
@Repository
public class ansDaoImpl implements ansDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<ansDto> ansList(int boardseq) {
		System.out.println("dato_boardseq"+boardseq);
		return sqlSession.selectList(NAMESPACE+"listAnswer",boardseq);
	}

	@Override
	public void insertAns(ansDto adto) {	
		sqlSession.insert(NAMESPACE+"insertAnswer",adto);

	}

	@Override
	public void deleteAns(int ansno) {
		sqlSession.delete(NAMESPACE+"deleteAnswer",ansno);
	}

	@Override
	public List<ansDto> ansPaging(Criteria cri) {
		return sqlSession.selectList(NAMESPACE+"ansPaging",cri);
	}

	@Override
	public int countAnsListTotal(int boardseq) {
		return sqlSession.selectOne(NAMESPACE+"countAnsList",boardseq);
	}


}
