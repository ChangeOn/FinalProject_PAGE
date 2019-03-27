package com.page.cal.model.dao;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.page.cal.model.dto.CalDto;



@Repository
public class CalDaoImpl implements CalDao {
	
	@Autowired
	private SqlSessionTemplate calsqlSession;

	@Override
	public List<CalDto> selectList(String id) {
		
		List<CalDto> list=new ArrayList<CalDto>();
		//System.out.println("yyyyMMdd 확인 : "+ yyyyMMdd);
		
		//Map<String,String>map=new HashMap<String,String>();
		//map.put("id",id);
		//map.put("dates", yyyyMMdd);
		
		
		list=calsqlSession.selectList(NAMESPACE+"selectList",id);
		
		return list;
	}

	@Override
	public int insertCalBoard(CalDto dto) {
		
		int res=0;
		try {
			res=calsqlSession.insert(NAMESPACE+"insertCalBoard",dto);

		}catch(Exception e) {
			System.out.println("일정 추가 실패");
			e.printStackTrace();
		}
		
		return res;
	}
	
	@Override
	public CalDto selectOne(int seq) {
		
		CalDto dto=new CalDto();
		dto=calsqlSession.selectOne(NAMESPACE+"selectOne",seq);
		
		return dto;
	}

	@Override
	public int updateCalBoard(CalDto dto) {
		
		int res=0;
		try {
			res=calsqlSession.update(NAMESPACE+"updateCal",dto);
		}catch(Exception e) {
			System.out.println("수정 실패");
			e.printStackTrace();
		}
		
		
		return res;
	}

	@Override
	public int deleteCalBoard(String yyyyMMdd) {
		// TODO Auto-generated method stub
		return 0;
	}



}













