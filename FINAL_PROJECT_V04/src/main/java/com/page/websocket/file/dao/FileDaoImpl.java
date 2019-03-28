package com.page.websocket.file.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.page.board.model.dto.fileDto;
import com.page.websocket.file.dto.FileDto;

@Repository
public class FileDaoImpl implements FileDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<FileDto> FileSelectList() {
		
		List<FileDto> list = new ArrayList<FileDto>();		
		list = sqlSession.selectList(NAMESPACE+"fileSelectList");
		
		return list;
	}

	@Override
	public int FileInsert(fileDto dto) {		
		int res = 0;
		try {
			res = sqlSession.insert(NAMESPACE + "fileInsert", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return res;
	}

	@Override
	public fileDto FileSelectOne(int fileno) {
		
		fileDto dto = new fileDto();
		dto = sqlSession.selectOne(NAMESPACE + "fileSelectOne", fileno);		
		return dto;
	}

	@Override
	public int FileSelectFileno(String filesavename) {		
		int res = 0;
		try {
			res = sqlSession.selectOne(NAMESPACE + "fileSelectFileno", filesavename);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return res;
	}

}
