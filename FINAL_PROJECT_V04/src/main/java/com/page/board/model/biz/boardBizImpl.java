package com.page.board.model.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page.board.model.dao.boardDao;
import com.page.board.model.dto.Criteria;
import com.page.board.model.dto.SearchCriteria;
import com.page.board.model.dto.boardDto;
import com.page.board.model.dto.fileDto;
@Service
public class boardBizImpl implements boardBiz {
	
	
	@Autowired
	private boardDao bdao;
	
	@Override
	public List<boardDto> selectList() {
		return bdao.selectList();
	}

	@Override
	public boardDto selectOne(int boardseq) {
		bdao.updateViewCnt(boardseq);
		return bdao.selectOne(boardseq);
	}

	@Override
	public int insertBoard(boardDto dto) {

		return bdao.insertBoard(dto);
	}

	@Override
	public int updateBoard(boardDto dto) {
	
		return bdao.updateBoard(dto);
	}

	@Override
	public int deleteBoard(int boardseq) {
	
		return bdao.deleteBoard(boardseq);
	}

	@Override
	public List<boardDto> listPaging(Criteria cri) {
		return bdao.listPaging(cri);
	}

	@Override
	public int countBoardListTotal() {
		return bdao.countBoardList();
	}

	@Override
	public List<boardDto> searchTitle(SearchCriteria scri) {
		return bdao.searchTitle(scri);
	}

	@Override
	public List<boardDto> searchContent(SearchCriteria scri) {
		return bdao.searchContent(scri);
	}

	@Override
	public List<boardDto> searchId(SearchCriteria scri) {
		return bdao.searchId(scri);
	}

	@Override
	public int countTitleList(String keyword) {
		return bdao.countTitleList(keyword);
	}

	@Override
	public int countContentList(String keyword) {
		return bdao.countContentList(keyword);
	}

	@Override
	public int countIdList(String keyword) {
		return bdao.countIdList(keyword);
	}

	@Override
	public int insertFile(fileDto fdto) {
		return bdao.insertFile(fdto);
	}



}
