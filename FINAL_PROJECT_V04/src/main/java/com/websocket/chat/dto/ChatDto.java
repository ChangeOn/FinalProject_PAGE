package com.websocket.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
	
	private int chatno;
	private int userno;
	private int fileno;
	private String chattype;
	private String chatcontent;
	private String videourl;
	private String chatcolor;

}
