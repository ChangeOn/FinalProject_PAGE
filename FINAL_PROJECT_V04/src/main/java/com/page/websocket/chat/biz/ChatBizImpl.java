package com.page.websocket.chat.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page.user.dto.UserVO;
import com.page.websocket.chat.dao.ChatDao;
import com.page.websocket.chat.dto.ChatDto;

@Service
public class ChatBizImpl implements ChatBiz {
	
	@Autowired
	private ChatDao dao;
	
	@Override
	public List<ChatDto> selectList() {
		return dao.ChatSelectList();
	}

	@Override
	public int insert(ChatDto dto) {		
		return dao.ChatInsert(dto);
	}

	@Override
	public List<ChatDto> ChatSelectPageList(int pageno) {
		return dao.ChatSelectPageList(pageno);
	}

	@Override
	public UserVO chatSelectID(int user_no) {
		return dao.chatSelectID(user_no);
	}

	@Override
	public int CheckSaveChat(int user_no) {		
		return dao.CheckSaveChat(user_no);
	}

	@Override
	public int CheckSavePaint(int user_no) {
		return dao.CheckSavePaint(user_no);
	}

}
