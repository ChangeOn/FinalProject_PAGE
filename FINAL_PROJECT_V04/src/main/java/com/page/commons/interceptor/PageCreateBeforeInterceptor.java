package com.page.commons.interceptor;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.page.model.biz.PageService;
import com.page.model.dto.PageVO;
import com.page.model.dto.PageWithUserVO;
import com.page.user.dto.UserVO;

public class PageCreateBeforeInterceptor extends HandlerInterceptorAdapter {

	@Inject
	private PageService page_service;
	
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
    	//HTTP SESSION 객체 생성
        HttpSession http_session = request.getSession();
        
        /* 세션을 통해 로그인된 유저 정보 및 페이지 이름 파악하기 */
		UserVO user_vo = (UserVO) http_session.getAttribute("login");
		String page_name = request.getParameter("page_name");
		
		/* 해당 유저 고유 번호와 페이지 이름으로 유저가 생성한 페이지 중,
		 * 중복된 값 검사 */
		PageWithUserVO page_with_user_vo = new PageWithUserVO();
		page_with_user_vo.setUser_no(user_vo.getUser_no());
		page_with_user_vo.setPage_name(page_name);
		
		//페이지 이름 중복 여부 검사
		int count_same_page_name = page_service.checkOverlabPageName(page_with_user_vo);
		if(count_same_page_name > 0) {
			//중복된 경우
			response.sendRedirect("/page");
			return false;
		}
		else {
			return true;
		}
	}
}
