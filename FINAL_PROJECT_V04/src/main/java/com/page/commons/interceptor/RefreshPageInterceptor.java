package com.page.commons.interceptor;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.page.bookmark.model.biz.BookmarkService;
import com.page.bookmark.model.dto.BookmarkVO;
import com.page.model.biz.PageService;
import com.page.model.dto.PageVO;
import com.page.user.biz.UserService;
import com.page.user.dto.UserVO;

public class RefreshPageInterceptor  extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Inject
	private UserService user_service;
	@Inject
	private PageService page_service;
	@Inject
	private BookmarkService bookmark_service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
        logger.info("메인 페이지 진입 전 유저 및 페이지 정보 최신화");
		
    	/* 
    	 * 메인 페이지에 진입하기 전 최신화 작업
    	 * 유저 정보 및 페이지 목록 최신화
    	 */
    	
    	//HTTP SESSION 객체 생성
        HttpSession http_session = request.getSession();
        
        /* 세션에서 유저정보 가져오기 추후 모델로 전달받기로 변경 예정 */
        Object before_user_vo = http_session.getAttribute("login");
        UserVO user_vo = (UserVO) before_user_vo;
		
        //유저 정보를 토대로 페이지 목록 및 북마크 조회
        List<PageVO> user_page_lsit = page_service.searchUserPages(user_vo);
        System.out.println(user_vo);
        List<BookmarkVO> bookmark_list = bookmark_service.searchPageBookmarks(user_vo);
        
        http_session.setAttribute("pages", user_page_lsit);
        http_session.setAttribute("bookmarks", bookmark_list);

        System.out.println(user_page_lsit);
        System.out.println(bookmark_list);
	}
}
