package com.page.cal.model.dao;

import java.util.List;

import com.page.cal.model.dto.CalDto;

public interface CalDao {
	
	String NAMESPACE="cal.";
	
	public List<CalDto> selectList(String id);
	public int insertCalBoard(CalDto dto);
	public int updateCalBoard(CalDto dto);
	public int deleteCalBoard(String yyyyMMdd);
	public CalDto selectOne(int seq);
	

}
