package com.page.commons.interceptor;

import com.page.user.dto.UserVO;
import com.page.user.biz.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private final UserService userService;

    @Inject
    public AuthInterceptor(UserService userService) {
        this.userService = userService;
    }

    // 페이지 요청 정보 저장
    private void saveDestination(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        if (query == null || query.equals("null")) {
            query = "";
        } else {
            query = "?" + query;
        }

        if (request.getMethod().equals("GET")) {
            logger.info("destination : " + (uri + query));
            request.getSession().setAttribute("destination", uri + query);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	System.out.println("SYSTEM: AuthInterceptor' preHandle");

    	//HTTP SESSION 객체 생성
        HttpSession http_session = request.getSession();
        
        /* 
         * 세션에 로그인 처리를 담당하는 사용자 객체가 남아있는지
         * 여부를 파악한다.
         * */
        if (http_session.getAttribute("login") == null) {
        	
        	//세션에 유지중인 로그인 정보가 없는 경우
            logger.info("current user is not logged");
            
            //인증 시도가 있었던 페이지 경로를 저장한다.
            saveDestination(request);
            
            /* 생성되었던 쿠키가 있는지 여부를 파악한다. */
    		Cookie login_cookie_old = WebUtils.getCookie(request, "login_cookie");
    		
            if (login_cookie_old != null) {
            	
            	/*
            	 * 생성되어있는 동일 쿠키 세션 정보를 토대로 유저목록을 조회하고,
            	 * 쿠키 유지 기간이 지나지 않은 유저정보를 조회한다.
            	 */
                UserVO user_vo = userService.checkLoginBefore(login_cookie_old.getValue());
                
                //유저정보 조회에 성공한 경우
                if (user_vo != null) {
                	
                    //로그인 정보 쿠키 재생성 및 최신화
                    Cookie loginCookie = new Cookie("login_cookie", http_session.getId());
                    loginCookie.setPath("/");
                    loginCookie.setMaxAge(60*60*24*7);
                    // 전송
                    response.addCookie(loginCookie);
                    http_session.setAttribute("login", user_vo);
                    return true;
                }
                //유저정보 조회에 실패한 경우
                else {
                	response.sendRedirect("/");
                	return false;
                }
            //쿠키 정보가 남아있지 않은 경우
            }
            else {
            	response.sendRedirect("/");
            	return false;
            }
        }
        /*
         * 로그인 상태가 유지 중인 경우, 별도의 조치 없이
         * 해당 페이지로 이동 시킨다.
         */
        else {
        	
        	return true;
        }
        
        /* 위의 쿠키는 남아있지만 유저정보 조회에 실패한 경우, 
         * 쿠키 정보도 남아 있지 않은 경우는 컨트롤 수행을 중단시키고 인덱스 페이지로 이동시킨다.  
         *  */
    }
}
