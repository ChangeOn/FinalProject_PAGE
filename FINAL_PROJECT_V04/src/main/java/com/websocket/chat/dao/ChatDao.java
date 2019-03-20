package com.websocket.chat.dao;

import java.util.List;

import com.websocket.chat.dto.ChatDto;

public interface ChatDao {
	
	String NAMESPACE = "com.websocket.chat.Mapper.";
	
	public List<ChatDto> ChatSelectList();	
	public int ChatInsert(ChatDto dto);

}
