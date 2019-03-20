package com.websocket.chat.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websocket.chat.dao.ChatDao;
import com.websocket.chat.dto.ChatDto;

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

}
