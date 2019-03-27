package com.page.board.model.dao;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.page.board.model.dto.Criteria;
import com.page.board.model.dto.SearchCriteria;
import com.page.board.model.dto.boardDto;
import com.page.board.model.dto.fileDto;
@Repository
public class boardDaoImpl implements boardDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<boardDto> selectList() {
		
		List<boardDto> boardlist = new ArrayList<boardDto>();
		
		boardlist = sqlSession.selectList(NAMESPACE+"selectList");
		
		return boardlist;
	}

	@Override
	public boardDto selectOne(int boardseq) {
		
		boardDto bdto = new boardDto();
		
		bdto = sqlSession.selectOne(NAMESPACE+"selectOne",boardseq);
		
		return bdto; 
	}

	@Override
	public int insertBoard(boardDto dto) {
		int res = 0;
		try {
			res = sqlSession.insert(NAMESPACE+"insertBoard",dto);
			
		} catch (Exception e) {
			System.out.println("insertboard error");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int updateBoard(boardDto dto) {
		int res = 0;
		System.out.println("DAOupdate = "+dto.getBoardseq()+dto.getTitle());
		try {
			res = sqlSession.update(NAMESPACE+"updateBoard",dto);
		} catch (Exception e) {
			System.out.println("updateboard error");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int deleteBoard(int boardseq) {
		int res = 0;
		
		try {
			res = sqlSession.delete(NAMESPACE+"deleteBoard",boardseq);
		} catch (Exception e) {
			System.out.println("deleteboard error");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void updateViewCnt(int boardseq) {
		int res = 0;
		
		try {
			res = sqlSession.update(NAMESPACE+"updateViewCnt",boardseq);
		} catch (Exception e) {
			System.out.println("viewCnt error");
			e.printStackTrace();
		}
		System.out.println("ViewCnt res = "+res);
		
	}

	@Override
	public List<boardDto> listPaging(Criteria cri) {
		
		return sqlSession.selectList(NAMESPACE+"listPaging",cri);
	}

	@Override
	public int countBoardList() {
	
		return sqlSession.selectOne(NAMESPACE+"countBoardList");
	}

	@Override
	public List<boardDto> searchTitle(SearchCriteria scri) {

		return sqlSession.selectList(NAMESPACE+"searchTitle",scri);
	}

	@Override
	public List<boardDto> searchContent(SearchCriteria scri) {
		return sqlSession.selectList(NAMESPACE+"searchContent",scri);
	}

	@Override
	public List<boardDto> searchId(SearchCriteria scri) {
		return sqlSession.selectList(NAMESPACE+"searchId",scri);
	}


	@Override
	public int countTitleList(String keyword) {
		return sqlSession.selectOne(NAMESPACE+"countTitleList",keyword);
	}

	@Override
	public int countContentList(String keyword) {
		return sqlSession.selectOne(NAMESPACE+"countContentList",keyword);
	}

	@Override
	public int countIdList(String keyword) {
		return sqlSession.selectOne(NAMESPACE+"countIdList",keyword);
	}

	@Override
	public int insertFile(fileDto fdto) {
		int res = 0;
		try {
			res = sqlSession.insert(NAMESPACE+"insertFile",fdto);
			
		} catch (Exception e) {
			System.out.println("insertFile error");
			e.printStackTrace();
		}
		return res;
	}




	
	
}
