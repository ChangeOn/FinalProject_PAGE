package com.page.board.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class photoDto {
	
	private MultipartFile filedata;
	private String callback;
	private String callback_func;
	public MultipartFile getFiledata() {
		return filedata;
	}
	public void setFiledata(MultipartFile filedata) {
		this.filedata = filedata;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getCallback_func() {
		return callback_func;
	}
	public void setCallback_func(String callback_func) {
		this.callback_func = callback_func;
	}

	
}
