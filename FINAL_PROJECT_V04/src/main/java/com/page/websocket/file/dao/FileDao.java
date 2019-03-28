package com.page.websocket.file.dao;

import java.util.List;

import com.page.board.model.dto.fileDto;
import com.page.websocket.file.dto.FileDto;

public interface FileDao {
	
	String NAMESPACE = "com.websocket.file.Mapper.";
	
	public List<FileDto> FileSelectList();	
	public int FileInsert(fileDto dto);
	public fileDto FileSelectOne(int fileno);
	public int FileSelectFileno(String filesavename);
	
}
