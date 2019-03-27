package com.page.commons.interceptor;

import com.page.user.dto.UserVO;
import com.page.user.biz.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RememberMeInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RememberMeInterceptor.class);

	@Inject
	private UserService userService;

	/* 로그인 컨트롤러가 호출될지 여부 판단 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("SYSTEM: RememberMeInterceptor' preHandle");

		HttpSession httpSession = request.getSession();
		
		//쿠키 정보 얻기
		Cookie login_cookie_old = WebUtils.getCookie(request, "login_cookie");
		
		//이미 생성된 쿠키 존재 여부 파악
        if (login_cookie_old != null) {
        	
        	//쿠키 값 비교로 로그인 유저 찾기
            UserVO userVO = userService.checkLoginBefore(login_cookie_old.getValue());
            
            if (userVO != null) {
            	
                //로그인 정보 쿠키 재생성
                Cookie loginCookie = new Cookie("loginCookie", httpSession.getId());
                loginCookie.setPath("/");
                loginCookie.setMaxAge(60*60*24*7);
                // 전송
                response.addCookie(loginCookie);
                httpSession.setAttribute("login", userVO);
                return true;
            }
        }

        return false;
	}
}
