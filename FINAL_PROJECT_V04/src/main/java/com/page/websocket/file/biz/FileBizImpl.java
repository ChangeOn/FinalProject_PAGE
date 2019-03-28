package com.page.websocket.file.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page.board.model.dto.fileDto;
import com.page.websocket.file.dao.FileDao;
import com.page.websocket.file.dto.FileDto;

@Service
public class FileBizImpl implements FileBiz {
	
	@Autowired
	private FileDao dao;
	
	@Override
	public List<FileDto> FileSelectList() {		
		return dao.FileSelectList();
	}

	@Override
	public int FileInsert(fileDto dto) {
		return dao.FileInsert(dto);
	}

	@Override
	public fileDto FileSelectOne(int fileno) {		
		return dao.FileSelectOne(fileno);
	}

	@Override
	public int FileSelectFileno(String filesavename) {		
		return dao.FileSelectFileno(filesavename);
	}

}
