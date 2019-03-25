package com.websocket.file.dto;

public class FileDto {
	
	private int fileno;
	private String filename;
	private String filesavename;
	
	public FileDto(int fileno, String filename, String filesavename) {
		super();
		this.fileno = fileno;
		this.filename = filename;
		this.filesavename = filesavename;
	}
	public FileDto() {
		super();
	}
	
	public int getFileno() {
		return fileno;
	}
	public void setFileno(int fileno) {
		this.fileno = fileno;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilesavename() {
		return filesavename;
	}
	public void setFilesavename(String filesavename) {
		this.filesavename = filesavename;
	}
	
	
	
	
	

}
