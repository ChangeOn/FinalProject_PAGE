package com.page.cal.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page.cal.model.dao.CalDao;
import com.page.cal.model.dto.CalDto;

@Service
public class CalBizImpl implements CalBiz {
	
	@Autowired
	private CalDao dao;

	@Override
	public List<CalDto> selectList(String id) {
	
		return dao.selectList(id);
	}

	@Override
	public int insertCalBoard(CalDto dto) {
	
		return dao.insertCalBoard(dto);
	}

	@Override
	public int updateCalBoard(CalDto dto) {
		
		return dao.updateCalBoard(dto);
	}

	@Override
	public int deleteCalBoard(String yyyyMMdd) {
		// TODO Auto-generated method stub
		return dao.deleteCalBoard(yyyyMMdd);
	}

	@Override
	public CalDto selectOne(int seq) {
		// TODO Auto-generated method stub
		return dao.selectOne(seq);
	}


}
