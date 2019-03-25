package com.websocket.chat.dto;

import java.util.Date;

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
	private int pageno;
	private String chattype;
	private String chatcontent;
	private String videourl;
	private int fileno;	
	private String chatcolor;
	private Date chattime;
	private int canvasno;
	private int mapno;
	

}
