package com.page.websocket.file.biz;

import java.util.List;

import com.page.board.model.dto.fileDto;
import com.page.websocket.file.dto.FileDto;

public interface FileBiz {
	
	public List<FileDto> FileSelectList();	
	public int FileInsert(fileDto dto);
	public fileDto FileSelectOne(int fileno);
	public int FileSelectFileno(String filesavename);
}
