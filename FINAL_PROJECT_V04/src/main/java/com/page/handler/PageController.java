package com.page.handler;

import com.page.user.dto.UserVO;
import com.page.model.biz.PageService;
import com.page.model.dto.PageVO;
import com.page.model.dto.PageWithUserVO;
import com.page.model.dto.Page_CreateVO;

import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/page")
public class PageController {
	
    private final PageService page_service;

    @Inject
    public PageController(PageService page_service) {
    	System.out.println("SYSTEM: PageController' @Inject");
    	System.out.println("SYSTEM: PageController'" + page_service);
        this.page_service = page_service;
    }


    // 페이지 메인 화면으로 이동
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String pageGET() throws Exception {

        return "/page/page";
    }
    
    /*
     *  클라이언트가 편집을 종료할 경우 AJAX를 통해 JSON 오브젝트를 전달하고,
     *  파라미터로 받아 데이터베이스에 저장한다.
     *  페이지는 유저별로 다르기 때문에 페이지 생성 정보와 함께 페이지 정보를 담는다.
     *  */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void pagePOST(HttpSession http_session, HttpServletResponse response,
    		PageVO page_vo, Model model ) throws Exception {
    	
    	/* 세션에 로그인 정보를 통해 유저정보를 얻고, 유저정보를 토대로
    	 * 페이지 정보 및, 생성 정보를 만든다. */
    	
    	/* Q. 유저당 페이지 개수는 정해져 있는데, 같은 페이지는 덮어쓰게 해야하는 것 아닌가?
    	 *  */
    	
    	/* 로그인 유지 여부는 인증 인터셉터가 처리하고, 데이터베이스 관련 생성 여부는
    	 * 관련 서비스 BIZ에서 처리하도록 한다. */
    	
    	Object object = http_session.getAttribute("login");
    	
		//로그인 정보를 통해 유저정보 받기
		UserVO user_vo = (UserVO) object;
		
		//페이지 정보 생성하기
		page_service.savePageContent(page_vo);
		
		//페이지 생성 정보 생성하기
    	Page_CreateVO page_create_vo = new Page_CreateVO();
    	page_create_vo.setPage_no(page_vo.getPage_no());
    	page_create_vo.setUser_no(user_vo.getUser_no());
    	page_service.connectUserWithPage(page_create_vo);
    }
    
    /*
     *  새로운 페이지 탭을 생성할 경우 페이지는 3개까지 제한하며,
     *  페이지 생성 정보를 통해 페이지 정보와 유저 정보를 바인딩한다.
     * 
     *  새로 생성될 페이지 탭은 기본이름을 가지고 EMPTY_CLOB 메소드를 통해
     *  CLOB의 공간을 확보하며, 유저별 페이지 탭 이름의 중복을 방지하도록 한다. 
     *  */
    @RequestMapping(value = "/new_tab", method = RequestMethod.POST)
    public void newTabPOST(HttpSession http_session, HttpServletResponse response,
    		HttpServletRequest request ,PageVO page_vo, Model model ) throws Exception {
    	
    	/* 세션에 로그인 정보를 통해 유저정보를 얻고, 유저정보를 토대로
    	 * 페이지 정보 및, 생성 정보를 만든다. */
    	
    	Object object = http_session.getAttribute("login");
    	
		//로그인 정보를 통해 유저정보 받기
		UserVO user_vo = (UserVO) object;
		
		/* 페이지 이름 중복 검사는 컨트롤러가 
		 * 실행되기 전 인터셉터에서 가로채 수행한다. */
		
		//페이지 정보 생성하기
		page_service.savePageContent(page_vo);
		
		//페이지 생성 정보 생성하기
    	Page_CreateVO page_create_vo = new Page_CreateVO();
    	page_create_vo.setPage_no(page_vo.getPage_no());
    	page_create_vo.setUser_no(user_vo.getUser_no());
    	page_service.connectUserWithPage(page_create_vo);
    	
    	model.addAttribute("user", user_vo);
    	
    	request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();

        JSONObject json_object = new JSONObject();
        json_object.put("message", "true");
        out.print(json_object);
    }
    
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public void loadPagePOST(HttpSession http_session, HttpServletResponse response,
    		HttpServletRequest request ,PageWithUserVO page_with_user_vo, Model model ) throws Exception {
    	
    	/* 
    	 * 파라미터로 얻은 페이지 이름 정보와 세션에 등록된 유저정보를 토대로,
    	 * 데이터베이스에서 해당 유저의 해당 페이지 정보를 불러온다.
    	 *  */
    	
    	//로그인 정보를 통해 유저정보 받기
    	Object before_user_vo = http_session.getAttribute("login");
		UserVO user_vo = (UserVO) before_user_vo;
		
		page_with_user_vo.setUser_no(user_vo.getUser_no());
		PageVO page_vo = page_service.loadPageContent(page_with_user_vo);
    	
    	request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();

        JSONObject json_object = new JSONObject();
        json_object.put("page_content", page_vo.getPage_content());
        out.print(json_object);
    }
}

