package com.page.cal.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.page.cal.model.biz.CalBiz;
import com.page.cal.model.dto.CalDto;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CalController {
	
	@Autowired
	private CalBiz cbiz;
	
	@RequestMapping(value="/calDBEvent.do", method= { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String getCal(String id, HttpServletRequest request) throws IOException {
		
		ModelAndView mv=new ModelAndView();
		
		String jsonMsg=null;
		// 나의 일정 data 추출
		List<CalDto> mydtos=cbiz.selectList(id);
		// json으로 만들기
		
		JSONObject jobject=new JSONObject();
		JSONArray jarr=new JSONArray();
		
		CalDto cdto=new CalDto();
		
		for(int i=0;i<mydtos.size();i++) {
			cdto=mydtos.get(i);
			JSONObject obj=new JSONObject();
			
			obj.put("seq",cdto.getSeq());
			obj.put("id",cdto.getId());
			obj.put("startdate", cdto.getStartdate());
			obj.put("enddate", cdto.getEnddate());
			obj.put("title",cdto.getTitle());
		
			
			jarr.add(obj);
			}
		
			ObjectMapper mapper=new ObjectMapper();
			jsonMsg=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jarr);
			System.out.println(jsonMsg);
			
		mv.setViewName("index");
		mv.addObject("caljson",jsonMsg);
			
			
		return jsonMsg;
	}
	
	@RequestMapping(value="/insertCal.do",method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String insertCal(Model model, String seq,String id,String startdate,String enddate,String title,String content, HttpServletResponse response) throws IOException {
		
		
		CalDto cdto=new CalDto();
		cdto.setSeq(seq);
		cdto.setId(id);
		cdto.setStartdate(startdate);
		cdto.setEnddate(enddate);
		cdto.setTitle(title);
		cdto.setContent(content);
		
		System.out.println(cdto.getSeq());
		System.out.println(cdto.getId());
		System.out.println(cdto.getTitle());
		System.out.println(cdto.getContent());
		System.out.println(cdto.getStartdate());
		System.out.println(cdto.getEnddate());
		
		int res=cbiz.insertCalBoard(cdto);

		if(res>0) {
			System.out.println("새로운 일정 추가 성공");
			return "redirect:test.do";

		}else {
			System.out.println("새로운 일정 추가 실패");
			return "redirect:test.do";
		}
	}


}










