package com.page.websocket.chat.dto;

import java.util.Date;

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
	
	
	public ChatDto() {
		super();
	}
	public ChatDto(int chatno, int userno, int pageno, String chattype, String chatcontent, String videourl, int fileno,
			String chatcolor, Date chattime, int canvasno, int mapno) {
		super();
		this.chatno = chatno;
		this.userno = userno;
		this.pageno = pageno;
		this.chattype = chattype;
		this.chatcontent = chatcontent;
		this.videourl = videourl;
		this.fileno = fileno;
		this.chatcolor = chatcolor;
		this.chattime = chattime;
		this.canvasno = canvasno;
		this.mapno = mapno;
	}
	
	public int getChatno() {
		return chatno;
	}
	public void setChatno(int chatno) {
		this.chatno = chatno;
	}
	public int getUserno() {
		return userno;
	}
	public void setUserno(int userno) {
		this.userno = userno;
	}
	public int getPageno() {
		return pageno;
	}
	public void setPageno(int pageno) {
		this.pageno = pageno;
	}
	public String getChattype() {
		return chattype;
	}
	public void setChattype(String chattype) {
		this.chattype = chattype;
	}
	public String getChatcontent() {
		return chatcontent;
	}
	public void setChatcontent(String chatcontent) {
		this.chatcontent = chatcontent;
	}
	public String getVideourl() {
		return videourl;
	}
	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}
	public int getFileno() {
		return fileno;
	}
	public void setFileno(int fileno) {
		this.fileno = fileno;
	}
	public String getChatcolor() {
		return chatcolor;
	}
	public void setChatcolor(String chatcolor) {
		this.chatcolor = chatcolor;
	}
	public Date getChattime() {
		return chattime;
	}
	public void setChattime(Date chattime) {
		this.chattime = chattime;
	}
	public int getCanvasno() {
		return canvasno;
	}
	public void setCanvasno(int canvasno) {
		this.canvasno = canvasno;
	}
	public int getMapno() {
		return mapno;
	}
	public void setMapno(int mapno) {
		this.mapno = mapno;
	}
}
