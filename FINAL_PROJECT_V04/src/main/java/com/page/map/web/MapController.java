package com.page.map.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.page.map.dao.MapDao;
import com.page.map.dto.MapDto;


/**
 * Handles requests for the application home page.
 */
@Controller
public class MapController {
	
	@Autowired
	private MapDao mdao;
	
	@ResponseBody
	@RequestMapping(value="/select_map.do")
	public JSONArray select_map(Model model) {
		List<MapDto> list = new ArrayList<MapDto>();
		list = mdao.select_map();
		
		JSONArray jsonArray = new JSONArray();
		
		for(MapDto ele : list) {		
			JSONObject jsonMap = new JSONObject();
			jsonMap.put("latitude", ele.getLatitude());			
			jsonMap.put("longitude", ele.getLongitude());
			jsonMap.put("addr",	ele.getAddr());
			jsonMap.put("jibun", ele.getJibun());
			jsonArray.add(jsonMap);
		}
		return jsonArray;
	}

	@RequestMapping("/search02.do")
	public String map() {				
		return "map04";
	}
	
	@ResponseBody
	@RequestMapping(value="/insert_map.do", method={RequestMethod.POST})
	public Map<String, Object> insert_map(@RequestParam Map<String, Object> geoinfo) {
		System.out.println(geoinfo);
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject = geoinfo;
		
		System.out.println("string"+(String)jsonObject.get("lat"));
		
		MapDto mdto = new MapDto();
		mdto.setLatitude(Double.parseDouble((String)jsonObject.get("lat")));
		mdto.setLongitude(Double.parseDouble((String)jsonObject.get("lng")));
		mdto.setAddr((String)jsonObject.get("addr"));
		mdto.setJibun((String)jsonObject.get("jibun"));
		
		int res = mdao.insert_map(mdto);

		if(res>0) {			
			return jsonObject;
		} else {
			return null;	
		}
	}	
}
