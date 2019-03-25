package com.websocket.chat.biz;

import java.util.List;

import com.websocket.chat.dto.ChatDto;

public interface ChatBiz {	
	
	public List<ChatDto> selectList();	
	public int insert(ChatDto dto);
	public List<ChatDto> ChatSelectPageList(int pageno);

}
