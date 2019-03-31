package com.page.websocket.chat.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.page.user.dto.UserVO;
import com.page.websocket.chat.dto.ChatDto;

@Repository
public class ChatDaoImpl implements ChatDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<ChatDto> ChatSelectList() {
		List<ChatDto> list = new ArrayList<ChatDto>();
		
		list = sqlSession.selectList(NAMESPACE+"chatSelectList");
		
		return list;
	}

	@Override
	public int ChatInsert(ChatDto dto) {
		int res = 0;
		try {
			res = sqlSession.insert(NAMESPACE + "chatInsert", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public List<ChatDto> ChatSelectPageList(int pageno) {
		List<ChatDto> list = new ArrayList<ChatDto>();
		
		list = sqlSession.selectList(NAMESPACE+"chatSelectPageList",pageno);
		
		return list;
	}

	@Override
	public UserVO chatSelectID(int user_no) {
		UserVO dto = new UserVO();
		
		dto  = sqlSession.selectOne(NAMESPACE+"chatSelectID",user_no);
		
		return dto;		
	}

	@Override
	public int CheckSaveChat(int user_no) {
		int res = 0;
		
		res = sqlSession.selectOne(NAMESPACE+"CheckSaveChat",user_no);
		return res;
	}

	@Override
	public int CheckSavePaint(int user_no) {
		int res = 0;
		
		res = sqlSession.selectOne(NAMESPACE+"CheckSavePaint",user_no);
		
		return res;
	}

	

	
}
