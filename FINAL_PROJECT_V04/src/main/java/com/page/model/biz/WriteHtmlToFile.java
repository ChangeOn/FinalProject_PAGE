package com.page.model.biz;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteHtmlToFile {

	public void write(String html) throws IOException {
		
		BufferedOutputStream bs = null;
		try {
			bs = new BufferedOutputStream(new FileOutputStream("D:/Eclipse/Java/Output.txt"));
			bs.write(html.getBytes()); //Byte형으로만 넣을 수 있음

		} catch (Exception e) {
	                e.getStackTrace();
			// TODO: handle exception
		}finally {
			bs.close(); //반드시 닫는다.
		} 
	}
}
