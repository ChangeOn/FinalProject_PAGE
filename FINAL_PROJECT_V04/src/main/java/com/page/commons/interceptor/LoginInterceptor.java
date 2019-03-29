package com.page.commons.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.page.model.biz.PageService;
import com.page.model.dto.PageVO;
import com.page.user.biz.UserService;
import com.page.user.dto.UserVO;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Inject
	private UserService user_service;
	
    private static final String LOGIN = "login";
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    	/* 
    	 * 로그인 후처리 인터셉터가 하는 작업
    	 * 
    	 */
    	
    	//HTTP SESSION 객체 생성
        HttpSession http_session = request.getSession();
        
        /* 클라이언트에서 전해받은 파라미터로 조회한 유저정보 가져오기 */
        ModelMap model_map = modelAndView.getModelMap();
        Object before_user_vo =  model_map.get("user");
        UserVO user_vo = (UserVO) before_user_vo;

        //로그인에 성공했을 경우
        if (user_vo != null) {
        	
        	//세션에 로그인한 유저 정보 등록
            http_session.setAttribute(LOGIN, user_vo);
            
            /* 로그인 유지를 선택한 경우 쿠키 설정을 최신화 한다. */
            if (request.getParameter("user_cookie") != null) {
            	
                //쿠키 생성 및 유지기간 최신화
                Cookie login_cookie = new Cookie("login_cookie", http_session.getId());
                login_cookie.setPath("/");
                login_cookie.setMaxAge(60*60*24*7);
                
                //최신화된 쿠키 전송
                response.addCookie(login_cookie);
            }
            
            //페이지 컨트롤러로 리다이렉트
            response.sendRedirect("/page");
        }
    }

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		/* 
		 * 로그인 전처리 인터셉터가 하는 작업
		 * 1. 세션에 남아있는 로그인 정보 제거
		 * 2. 로그인 시도 전 유지 중인 쿠키 정보 최신화
		 * */

		//HTTP SESSION 객체 생성
		HttpSession http_session = request.getSession();
		
        /* 
         * 세션에 로그인 처리를 담당하는 사용자 객체가 남아있다면,
         * 새로운 로그인을 위해 제거한다.  
         * */
        if (http_session.getAttribute(LOGIN) != null) {
            http_session.removeAttribute(LOGIN);
        }

		//이전 쿠키 생성 여부 파악
		Cookie login_cookie_old = WebUtils.getCookie(request, "login_cookie");
		if (login_cookie_old != null) {
			/* 이미 쿠키가 존재하는 경우, 이전에 로그인때 생성된 쿠키가 존재한다는 것 */
			String session_key = login_cookie_old.getValue();
			
			/*
			 * 세션 아이디를 기준으로 동일 HTTP 세션 상에서 다른 계정으로 로그인 한 적이 있는지 파악한다, 
			 * 로그아웃을 하지 않고 종료시 DB상에 세션키값이 계속 남아있기 때문.
			 */
			if (user_service.countUserWithSameKey(session_key) > 0) {
				/*
				 * 동일 세션 상 이미 유지 중인 계정이 있는 경우, 
				 * 기존 계정의 세션 키값을 변경 해줌으로써 중복 로그인을 막을 수 있다.
				 */
				
				UserVO user_vo = user_service.checkLoginBefore(session_key);
				user_service.keepLogin(user_vo.getUser_id(), "NONE", new Date());
			}
		}
		
		return true;
	}
}
