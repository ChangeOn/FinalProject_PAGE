package com.page.websocket.file.biz;

import java.util.List;

import com.page.websocket.file.dto.FileDto;

public interface FileBiz {
	
	public List<FileDto> FileSelectList();	
	public int FileInsert(FileDto dto);
	public FileDto FileSelectOne(int fileno);
	public int FileSelectFileno(String filesavename);
}
