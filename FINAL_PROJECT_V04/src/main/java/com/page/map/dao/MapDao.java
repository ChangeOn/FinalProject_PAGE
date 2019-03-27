package com.page.map.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.page.map.dto.MapDto;

@Repository
public class MapDao {

	@Autowired
	private SqlSession session;
	
	String NAMESPACE = "com.page.map.mapper.";
	
	public List<MapDto> select_map() {
		List<MapDto> list = new ArrayList<MapDto>();
		list = session.selectList(NAMESPACE+"select_map");
		
		return list;		
	}
	
	public int insert_map(MapDto mdto) {
		
		int res = 0;
		res = session.insert(NAMESPACE+"insert_map", mdto);
	
		return res;
	}
	
}
