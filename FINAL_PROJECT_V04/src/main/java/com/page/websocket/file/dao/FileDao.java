package com.page.websocket.file.dao;

import java.util.List;

import com.page.websocket.file.dto.FileDto;

public interface FileDao {
	
	String NAMESPACE = "com.websocket.file.Mapper.";
	
	public List<FileDto> FileSelectList();	
	public int FileInsert(FileDto dto);
	public FileDto FileSelectOne(int fileno);
	public int FileSelectFileno(String filesavename);
	
}
