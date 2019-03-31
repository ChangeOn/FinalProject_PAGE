package com.page.bookmark.handler;

import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.page.bookmark.model.biz.BookmarkService;
import com.page.bookmark.model.dto.BookmarkVO;
import com.page.bookmark.model.dto.BookmarkWithPageVO;
import com.page.model.dto.PageWithUserVO;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {
	
	private final BookmarkService bookmark_service;

	@Inject
	public BookmarkController(BookmarkService bookmark_service) { 
		
		this.bookmark_service = bookmark_service;
	}
	
	/*
	 * 북마크 추가시 해당 페이지의 스크롤 위치와 북마크 이름을,
	 * 데이터베이스에 저장한다.
	 *  */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String BookmarkPOST(HttpSession http_session, HttpServletResponse response,
    		BookmarkVO bookmark_vo, Model model) throws Exception {

    	/*
    	 * 파라미터로 전해받은 북마크 정보를 토대로
    	 * 데이터 베이스에 저장
    	 *  */
    	bookmark_service.saveBookmark(bookmark_vo);
    	
    	return "redirect:/page";
    }
    
	/*
	 * 북마크 링크 클릭시 특정 북마크 번호를 토대로 페이지 정보를 조회한다.
	 *  */
    @RequestMapping(value = "load", method = RequestMethod.POST)
    public void loadBookmarkPOST(HttpSession http_session, HttpServletResponse response,
    		HttpServletRequest request, BookmarkWithPageVO bookmark_with_page_vo, Model model) throws Exception {

    	/*
    	 * 파라미터로 전해받은 북마크 정보를 토대로
    	 * 데이터 베이스에서 페이지 정보 조회
    	 *  */
    	System.out.println(bookmark_with_page_vo);
    	bookmark_with_page_vo = bookmark_service.loadPageBookmark(bookmark_with_page_vo);
    	System.out.println(bookmark_with_page_vo);
    	
    	request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();

        JSONObject json_object = new JSONObject();
        json_object.put("page_content", bookmark_with_page_vo.getPage_content());
        json_object.put("bookmark_scrolltop", bookmark_with_page_vo.getBookmark_scrolltop());
        json_object.put("page_no", bookmark_with_page_vo.getPage_no());
        out.print(json_object);
    }

}
