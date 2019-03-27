package com.page.cal.model.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Util {

	private String toDates;
	
	public String getToDates() {
		return toDates;
	}
	
	public void setToDates(String mdate) {
		
		String m=mdate.substring(0,4)+"-"
				+mdate.substring(4,6)+"-"
				+mdate.substring(6,8)+" "
				+mdate.substring(8,10)+":"
				+mdate.substring(10)+":00";
		
		// formatting (date → text), parsing (text → date) , Date를 내가 원하는 대로 출력하도록
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy년MM월dd일 HH시mm분");
		//date를 string object로 바꿔준다 : yyyy-mm-dd hh:mm:ss
		Timestamp tm=Timestamp.valueOf(m);
		toDates=sdf.format(tm);
	}
	
	// 한자리 수를 두자리 수로 변환
	public static String isTwo(String msg) {
		return(msg.length()<2)? "0"+msg : msg; 
	}
}
