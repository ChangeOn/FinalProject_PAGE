package com.page.websocket.chat.dao;

import java.util.List;

import com.page.websocket.chat.dto.ChatDto;

public interface ChatDao {
	
	String NAMESPACE = "com.page.websocket.chat.Mapper.";
	
	public List<ChatDto> ChatSelectList();
	public List<ChatDto> ChatSelectPageList(int pageno);
	public int ChatInsert(ChatDto dto);

}
